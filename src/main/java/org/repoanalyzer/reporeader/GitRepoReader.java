package org.repoanalyzer.reporeader;

import org.eclipse.jgit.api.Git;
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
    public GitRepoReader(String url){
        super(new File(url, ".git").toString());
        this.state = 'P';
    }

    public Future<List<Commit>> getCommits(){
        this.state = 'R';
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

        this.size = 0;
        for(RevCommit commit : commits) this.size++;

        try {
            commits = git.log().call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }

        final Iterable<RevCommit> finalCommits = commits;

        this.state = 'T';
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

        this.state = 'P';
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<List<Commit>> future = executor.submit(task);
        executor.shutdown();

        return future;
    }

    public Progress getProgress() {
        Progress progress = new Progress();
        progress.setState(this.state);
        progress.setWorkDone(this.progress.get());
        progress.setMax(this.size);
        //progress.setProgressFraction(((float) this.progress.get()) / this.size);
        return progress;
    }

    public static void main(String[] args){
        GitRepoReader repoReader = new GitRepoReader("/home/linux/Desktop/Repo-Analyzer/.git");
        Future<List<Commit>> future = repoReader.getCommits();

        while(!future.isDone()) {
            //System.out.println(repoReader.getProgress().getProgressFraction());
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
