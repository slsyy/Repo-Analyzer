package org.repoanalyzer.reporeader.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.repoanalyzer.reporeader.AbstractRepoReader;
import org.repoanalyzer.reporeader.Progress;
import org.repoanalyzer.reporeader.exceptions.RepositoryNotFoundOrInvalidException;
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

    public Future<List<Commit>> getCommits() throws RepositoryNotFoundOrInvalidException {
        this.progress = new AtomicInteger();
        Git git = new Git(buildRepository());
        Iterable<RevCommit> commits = getRevCommitsFromRepository(git);
        this.size = 0;

        for(RevCommit commit : commits) this.size++;

        commits = getRevCommitsFromRepository(git);

        final Iterable<RevCommit> finalCommits = commits;

        Callable<List<Commit>> task = () -> {
            List<Commit> result = new LinkedList<>();
            AuthorProvider authorProvider = new AuthorProvider();

            for(RevCommit commit : finalCommits){
                this.progress.incrementAndGet();

                CommitBuilder commitBuilder = new CommitBuilder(authorProvider,
                                                                commit.getCommitterIdent().getName(),
                                                                commit.getCommitterIdent().getEmailAddress(),
                                                                commit.getName(),
                                                                new DateTime(((long) commit.getCommitTime()) * 1000),
                                                                commit.getFullMessage());
                result.add(commitBuilder.setAddedLinesNumber(0)
                                        .setDeletedLinesNumber(0)
                                        .setChangedLinesNumber(0)
                                        .createCommit());
            }

            return result;
        };

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<List<Commit>> future = executor.submit(task);
        executor.shutdown();

        return future;
    }

    public Progress getProgress() {
        Progress progress = new Progress();
        progress.setWorkDone(this.progress.get());
        progress.setMax(this.size);
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
}
