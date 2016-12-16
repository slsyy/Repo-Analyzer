package org.repoanalyzer.reporeader;

public class RepoReaderFactory {

    public static IRepoReader create(String url){
        return new GitRepoReader(url);
    }
}
