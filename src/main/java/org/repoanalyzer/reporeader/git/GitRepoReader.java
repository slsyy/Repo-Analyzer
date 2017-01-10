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
import org.repoanalyzer.reporeader.Progress;
import org.repoanalyzer.reporeader.commit.FilePreloadedAuthorProvider;
import org.repoanalyzer.reporeader.exceptions.*;
import org.repoanalyzer.reporeader.commit.AuthorProvider;
import org.repoanalyzer.reporeader.commit.Commit;

import java.io.File;
import java.io.IOException;

import org.joda.time.DateTime;
import org.repoanalyzer.reporeader.commit.CommitBuilder;

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

        for (RevCommit commit : this.getRevCommitsFromRepository(git)) this.size++;

        Callable<List<Commit>> task = () -> this.analyzeRepository(repo,
                                                                   this.prepareAuthorProvider(),
                                                                   this.getRevCommitsFromRepository(git));

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<List<Commit>> future = executor.submit(task);
        executor.shutdown();

        return future;
    }

    public Progress getProgress() {
        Progress progress = new Progress(this.progress.get(), this.size);
        return progress;
    }

    private Iterable<RevCommit> getRevCommitsFromRepository(Git git) throws RepositoryNotFoundOrInvalidException {
        Iterable<RevCommit> commits = null;
        try {
            commits = git.log().call();
        } catch (GitAPIException exception) {
            throw new RepositoryNotFoundOrInvalidException();
        }
        return commits;
    }

    private Repository buildRepository() throws RepositoryNotFoundOrInvalidException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        builder.setGitDir(new File(this.url))
               .readEnvironment()
               .findGitDir();
        Repository repo = null;
        try {
            repo = builder.build();
        } catch (IOException exception) {
            throw new RepositoryNotFoundOrInvalidException();
        }
        return repo;
    }

    private List<Commit> analyzeRepository(Repository repo,
                                           AuthorProvider authorProvider,
                                           final Iterable<RevCommit> commits) throws RepositoryNotFoundOrInvalidException {
        List<Commit> result = new LinkedList<>();

        DiffFormatter diffFormatter = new DiffFormatter(DisabledOutputStream.INSTANCE);
        diffFormatter.setRepository(repo);

        ObjectReader reader = repo.newObjectReader();

        for (RevCommit commit : commits) {
            this.progress.incrementAndGet();
            int addedLinesNumber = 0;
            int deletedLinesNumber = 0;
            int changedLinesNumber = 0;
            AbstractTreeIterator oldTreeIter;
            AbstractTreeIterator newTreeIter;

            try {
                newTreeIter = new CanonicalTreeParser(null, reader, commit.getTree());

                int parents = commit.getParentCount();

                if (parents < 1) oldTreeIter = new EmptyTreeIterator();
                else if (parents == 1) oldTreeIter = new CanonicalTreeParser(null, reader, commit.getParent(0).getTree());
                else continue;

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
                        }
                    }
                }
            } catch (IOException exception) {
                throw new RepositoryNotFoundOrInvalidException();
            }

            CommitBuilder commitBuilder = new CommitBuilder(authorProvider);

            try {
                result.add(commitBuilder.setAuthorName(commit.getCommitterIdent().getName())
                                        .setAuthorEmail(commit.getCommitterIdent().getEmailAddress())
                                        .setHashCode(commit.getName())
                                        .setDateTime(new DateTime(((long) commit.getCommitTime()) * 1000))
                                        .setMessage(commit.getFullMessage())
                                        .setAddedLinesNumber(addedLinesNumber)
                                        .setDeletedLinesNumber(deletedLinesNumber)
                                        .setChangedLinesNumber(changedLinesNumber)
                                        .createCommit());
            } catch (IncompleteCommitInfoException exception) {
                System.out.print(exception.getMessage());
                System.out.println(" - Skipping commit.");
            }
        }

        return result;
    }

    private AuthorProvider prepareAuthorProvider() {
        try {
            return new FilePreloadedAuthorProvider(this.authorFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Ignoring provided file with authors.");
            return new AuthorProvider();
        }
    }
}