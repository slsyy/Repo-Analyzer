package org.repoanalyzer.reporeader;

import org.repoanalyzer.reporeader.git.GitRepoReader;

public class RepoReaderFactory {

    public static IRepoReader create(String url){
        return new GitRepoReader(url);
    }
}
