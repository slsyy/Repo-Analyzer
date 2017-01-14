package org.repoanalyzer.reporeader.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
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
        this.progress = new AtomicInteger();
        this.size = 0;

        Repository repo = this.buildRepository();
        Git git = new Git(repo);

        final LinkedList<RevCommit> commits = this.getRevCommitsFromRepository(git);
        this.size = commits.size();

        prepareAuthorProvider();
        Callable<List<Commit>> task = () -> this.analyzeRepository(repo, commits);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<List<Commit>> future = executor.submit(task);
        executor.shutdown();

        return future;
    }

    private LinkedList<RevCommit> getRevCommitsFromRepository(Git git) throws RepositoryNotFoundOrInvalidException {
        Iterable<RevCommit> iterableCommits;
        try {
            iterableCommits = git.log().call();
        } catch (GitAPIException exception) {
            throw new RepositoryNotFoundOrInvalidException();
        }

        LinkedList<RevCommit> commits = new LinkedList<>();
        for (RevCommit commit : iterableCommits)
            commits.add(commit);

        return commits;
    }

    private Repository buildRepository() throws RepositoryNotFoundOrInvalidException {
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
        return repo;
    }

    private List<Commit> analyzeRepository(Repository repo, final LinkedList<RevCommit> commits)
            throws RepositoryNotFoundOrInvalidException {
        List<Commit> result = new LinkedList<>();

        DiffFormatter diffFormatter = new DiffFormatter(DisabledOutputStream.INSTANCE);
        diffFormatter.setRepository(repo);

        ObjectReader reader = repo.newObjectReader();

        for (RevCommit commit : commits) {
            this.progress.incrementAndGet();
            try {
                result.add(this.analyzeCommit(diffFormatter, reader, commit));
            } catch (MergeCommitException e) {
                continue;
            }
        }

        return result;
    }

    private Commit analyzeCommit (DiffFormatter diffFormatter, ObjectReader reader, RevCommit commit)
            throws MergeCommitException, RepositoryNotFoundOrInvalidException {

        int addedLinesNumber = 0;
        int deletedLinesNumber = 0;
        int changedLinesNumber = 0;

        try {
            int parents = commit.getParentCount();
            AbstractTreeIterator oldTreeIter;
            AbstractTreeIterator newTreeIter = new CanonicalTreeParser(null, reader, commit.getTree());

            if (parents < 1) oldTreeIter = new EmptyTreeIterator();
            else if (parents == 1) oldTreeIter = new CanonicalTreeParser(null, reader, commit.getParent(0).getTree());
            else throw new MergeCommitException();

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

        CommitBuilder commitBuilder = new CommitBuilder(authorProvider);
        Commit result = null;

        try {
            result = commitBuilder.setAuthorName(commit.getCommitterIdent().getName())
                    .setAuthorEmail(commit.getCommitterIdent().getEmailAddress())
                    .setHashCode(commit.getName())
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