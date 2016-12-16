package org.repoanalyzer.reporeader;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.repoanalyzer.reporeader.commit.Commit;
//import org.repoanalyzer.reporeader.commit.AuthorProvider;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
            repository = builder.setGitDir(new File("/home/linux/Desktop/Repo-Analyzer/.git"))
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

//        AuthorProvider authorProvider = new AuthorProvider();

        List<Commit> result = new LinkedList<Commit>();

        for(RevCommit commit : commits){
            System.out.println(commit.getId());
            System.out.println(commit.getCommitterIdent().getName());
            System.out.println(commit.getCommitterIdent().getEmailAddress());
            System.out.println(LocalDateTime.ofEpochSecond(commit.getCommitTime(), 0, ZoneOffset.of("+1")));
            System.out.print(commit.getFullMessage());
            System.out.println();
        }

        return null;
    }

    public Progress getProgress() {
        return null;
    }

    public static void main(String[] args){
        new GitRepoReader("cos").getCommits();
    }
}
