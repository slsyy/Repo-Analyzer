package org.repoanalyzer.reporeader;

import org.repoanalyzer.reporeader.git.GitRepoReader;

public class RepoReaderFactory {

    public static IRepoReader create(String url){ return new GitRepoReader(url); }
    public static IRepoReader create(String url, String authorFile){
        return new GitRepoReader(url, authorFile);
    }
}
