package org.repoanalyzer.reporeader.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.repoanalyzer.reporeader.AbstractRepoReader;
import org.repoanalyzer.reporeader.commit.*;
import org.repoanalyzer.reporeader.exceptions.*;

import java.io.File;
import java.io.IOException;

import org.joda.time.DateTime;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GitRepoReader extends AbstractRepoReader {
    private Git git;

    public GitRepoReader(String url) {
        super(new File(url, ".git").toString());
    }

    public GitRepoReader(String url, String authorFile) {
        super(new File(url, ".git").toString(), authorFile);
    }

    public Future<List<Commit>> getCommits() throws RepositoryNotFoundOrInvalidException,
                                                    JsonParsingException,
                                                    CannotOpenAuthorFileException,
                                                    InvalidJsonDataFormatException {
        this.git = this.buildGitRepository();

        List<RevCommit> commits = this.getRevCommitsFromRepository();
        this.size = commits.size();

        prepareAuthorProvider();
        Callable<List<Commit>> task = () -> this.analyzeRepository(commits);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<List<Commit>> future = executor.submit(task);
        executor.shutdown();

        return future;
    }

    private Git buildGitRepository() throws RepositoryNotFoundOrInvalidException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        builder.setGitDir(new File(this.url))
                .readEnvironment()
                .findGitDir();
        Repository repo;
        try {
            repo = builder.build();
        } catch (IOException exception) {
            throw new RepositoryNotFoundOrInvalidException();
        }
        return new Git(repo);
    }

    private List<RevCommit> getRevCommitsFromRepository() throws RepositoryNotFoundOrInvalidException {
        Iterable<RevCommit> iterableCommits;
        try {
            iterableCommits = this.git.log().setRevFilter(RevFilter.NO_MERGES).call();
        } catch (GitAPIException exception) {
            throw new RepositoryNotFoundOrInvalidException();
        }

        LinkedList<RevCommit> commits = new LinkedList<>();
        for (RevCommit commit : iterableCommits)
            commits.add(commit);

        return commits;
    }

    private List<Commit> analyzeRepository(final List<RevCommit> commits)
            throws RepositoryNotFoundOrInvalidException {
        Repository repo = this.git.getRepository();

        DiffFormatter diffFormatter = new DiffFormatter(DisabledOutputStream.INSTANCE);
        diffFormatter.setRepository(repo);
        ObjectReader reader = repo.newObjectReader();

        List<Commit> result = new LinkedList<>();

        for (RevCommit commit : commits) {
            this.progress.incrementAndGet();
            result.add(this.analyzeCommit(diffFormatter, reader, commit));
        }

        return result;
    }

    private Commit analyzeCommit (DiffFormatter diffFormatter, ObjectReader reader, RevCommit commit)
            throws RepositoryNotFoundOrInvalidException {

        int addedLinesNumber = 0;
        int deletedLinesNumber = 0;
        int changedLinesNumber = 0;

        try {
            int parents = commit.getParentCount();
            AbstractTreeIterator oldTreeIter;
            AbstractTreeIterator newTreeIter = new CanonicalTreeParser(null, reader, commit.getTree());

            if (parents < 1) oldTreeIter = new EmptyTreeIterator();
            else oldTreeIter = new CanonicalTreeParser(null, reader, commit.getParent(0).getTree());

            for (DiffEntry entry : diffFormatter.scan(oldTreeIter, newTreeIter)) {
                for (Edit edit : diffFormatter.toFileHeader(entry).toEditList()) {
                    switch (edit.getType()) {
                        case INSERT:
                            addedLinesNumber += edit.getLengthB();
                            break;
                        case DELETE:
                            deletedLinesNumber += edit.getLengthA();
                            break;
                        case REPLACE:
                            addedLinesNumber += edit.getLengthB();
                            deletedLinesNumber += edit.getLengthA();
                            changedLinesNumber += edit.getLengthA();
                            break;
                        case EMPTY:
                            break;
                    }
                }
            }
        } catch (IOException exception) {
            throw new RepositoryNotFoundOrInvalidException();
        }

        CommitBuilder commitBuilder = new CommitBuilder(this.authorProvider);
        Commit result = null;

        try {
            result = commitBuilder.setAuthorName(commit.getCommitterIdent().getName())
                    .setAuthorEmail(commit.getCommitterIdent().getEmailAddress())
                    .setSHA(commit.getName())
                    .setDateTime(new DateTime(((long) commit.getCommitTime()) * 1000))
                    .setMessage(commit.getFullMessage())
                    .setAddedLinesNumber(addedLinesNumber)
                    .setDeletedLinesNumber(deletedLinesNumber)
                    .setChangedLinesNumber(changedLinesNumber)
                    .createCommit();
        } catch (IncompleteCommitInfoException exception) {
            System.out.print(exception.getMessage());
            System.out.println(" - Skipping commit.");
        }

        return result;
    }
}