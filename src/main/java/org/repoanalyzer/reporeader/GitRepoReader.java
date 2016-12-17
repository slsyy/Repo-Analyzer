package org.repoanalyzer.reporeader;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
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

public class GitRepoReader extends AbstractRepoReader{
    private int size;
    private AtomicInteger progress;

    public GitRepoReader(String url){
        super(url);
    }
    
    public Future<List<Commit>> getCommits(){
        this.progress = new AtomicInteger();

        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        builder.setGitDir(new File(this.url))
                .readEnvironment()
                .findGitDir();
        Repository repository = null;

        try {
            repository = builder.build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Git git = new Git(repository);
        Iterable<RevCommit> commits = null;

        try {
            commits = git.log().call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }

        size = 0;
        for(RevCommit commit : commits) size++;

        try {
            commits = git.log().call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }

        final Iterable<RevCommit> finalCommits = commits;

        Callable<List<Commit>> task = () -> {
            List<Commit> result = new LinkedList<>();
            AuthorProvider authorProvider = new AuthorProvider();
            CommitBuilder commitBuilder = new CommitBuilder(authorProvider);

            for(RevCommit commit : finalCommits){
                this.progress.incrementAndGet();

                commitBuilder.setHashCode(commit.getName());
                commitBuilder.setMessage(commit.getFullMessage());
                commitBuilder.setDate(new DateTime(((long) commit.getCommitTime()) * 1000));
                commitBuilder.setAuthorName(commit.getCommitterIdent().getName());
                commitBuilder.setAuthorEmail(commit.getCommitterIdent().getEmailAddress());

                commitBuilder.setAuthor(null);
                commitBuilder.setAddedLinesNumber(0);
                commitBuilder.setDeletedLinesNumber(0);
                commitBuilder.setChangedLinesNumber(0);

                result.add(commitBuilder.createCommit());
            }
            Thread.sleep(5);
            return result;
        };

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<List<Commit>> future = executor.submit(task);
        executor.shutdown();

        return future;
    }

    public Progress getProgress() {
        Progress progress = new Progress();
        progress.setState("read");
        progress.setProgressFraction(((float) this.progress.get()) / this.size);
        return progress;
    }

    public static void main(String[] args){
        GitRepoReader repoReader = new GitRepoReader("/home/linux/Desktop/Repo-Analyzer/.git");
        Future<List<Commit>> future = repoReader.getCommits();

        while(!future.isDone()) {
            System.out.println(repoReader.getProgress().getProgressFraction());
        }

        List<Commit> res = null;
        try {
            res = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        for(Commit commit : res){
            System.out.println(commit + "\n");
        }
    }
}
