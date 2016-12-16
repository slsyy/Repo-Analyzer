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

public class GitRepoReader extends AbstractRepoReader{

    public GitRepoReader(String url){
        super(url);
    }

    public List<Commit> getCommits(){
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = null;
        try {
            repository = builder.setGitDir(new File(this.url))
                    .readEnvironment()
                    .findGitDir()
                    .build();
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

        List<Commit> result = new LinkedList<Commit>();
        AuthorProvider authorProvider = new AuthorProvider();
        CommitBuilder commitBuilder = new CommitBuilder(authorProvider);

        for(RevCommit commit : commits){
            commitBuilder.setHashCode("abc");
            commitBuilder.setAuthor(null);
            commitBuilder.setMessage(commit.getFullMessage());
            commitBuilder.setDate(new DateTime(((long) commit.getCommitTime()) * 1000));


            commitBuilder.setAddedLinesNumber(0);
            commitBuilder.setDeletedLinesNumber(0);
            commitBuilder.setChangedLinesNumber(0);

            result.add(commitBuilder.createCommit());

//            System.out.println(commit.getId());
//            System.out.println(commit.getCommitterIdent().getName());
//            System.out.println(commit.getCommitterIdent().getEmailAddress());
        }

        for(Commit commit : result){
            System.out.println(commit + "\n");
        }

        return result;
    }

    public Progress getProgress() {
        return null;
    }

    public static void main(String[] args){
        new GitRepoReader("/home/linux/Desktop/Repo-Analyzer/.git").getCommits();
    }
}
