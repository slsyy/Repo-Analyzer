package org.repoanalyzer.reporeader.git;

import org.repoanalyzer.reporeader.AbstractRepoReader;
import org.repoanalyzer.reporeader.author.AuthorProvider;
import org.repoanalyzer.reporeader.commit.*;
import org.repoanalyzer.reporeader.exceptions.*;


import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.util.io.DisabledOutputStream;

import org.joda.time.DateTime;

public class GitRepoReader extends AbstractRepoReader {
    private Git git;

    public GitRepoReader(String url, AuthorProvider authorProvider) {
        super(url, authorProvider);
    }

    public Future<List<Commit>> getCommits() throws RepositoryNotFoundOrInvalidException,
                                                    JsonParsingException,
                                                    CannotOpenAuthorFileException,
                                                    InvalidJsonDataFormatException {
        GitRepoBuilder gitRepoBuilder = new GitRepoBuilder(this.url);
        this.git = gitRepoBuilder.build();

        GitRevCommitsExtractor gitRevCommitsExtractor = new GitRevCommitsExtractor(this.git);
        List<RevCommit> commits = gitRevCommitsExtractor.getListOfRevCommitsFromRepository();
        this.size = commits.size();

        Callable<List<Commit>> task = () -> this.analyzeCommits(commits);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<List<Commit>> future = executor.submit(task);
        executor.shutdown();

        return future;
    }

    private List<Commit> analyzeCommits(final List<RevCommit> commits)
            throws RepositoryNotFoundOrInvalidException {
        DiffFormatter diffFormatter = new DiffFormatter(DisabledOutputStream.INSTANCE);
        diffFormatter.setRepository(this.git.getRepository());
        ObjectReader reader = this.git.getRepository().newObjectReader();

        List<Commit> result = new LinkedList<>();
        for (RevCommit commit : commits) {
            this.progress.incrementAndGet();
            try {
                result.add(this.analyzeCommit(diffFormatter, reader, commit));
            } catch (IncompleteCommitInfoException e) {
                System.out.print(e.getMessage());
                System.out.println(" - Skipping commit.");
            }
        }

        return result;
    }

    private Commit analyzeCommit (DiffFormatter diffFormatter, ObjectReader reader, RevCommit commit)
            throws RepositoryNotFoundOrInvalidException, IncompleteCommitInfoException {
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
        } catch (IOException e) {
            throw new RepositoryNotFoundOrInvalidException();
        }

        CommitBuilder commitBuilder = new CommitBuilder(this.authorProvider);

        return commitBuilder.setAuthorName(commit.getCommitterIdent().getName())
                    .setAuthorEmail(commit.getCommitterIdent().getEmailAddress())
                    .setSHA(commit.getName())
                    .setDateTime(new DateTime(((long) commit.getCommitTime()) * 1000))
                    .setMessage(commit.getFullMessage())
                    .setAddedLinesNumber(addedLinesNumber)
                    .setDeletedLinesNumber(deletedLinesNumber)
                    .setChangedLinesNumber(changedLinesNumber)
                    .createCommit();
    }
}