package org.repoanalyzer.reporeader;

import org.repoanalyzer.reporeader.author.AuthorProvider;
import org.repoanalyzer.reporeader.exceptions.RepositoryNotFoundOrInvalidException;
import org.repoanalyzer.reporeader.git.GitRepoReader;

public class RepoReaderFactory {

    public static IRepoReader create(String url, AuthorProvider authorProvider) throws RepositoryNotFoundOrInvalidException{
        if (url.endsWith(".git"))
            return new GitRepoReader(url, authorProvider);
        else throw new RepositoryNotFoundOrInvalidException("Couldn't find repository in given direction.");
    }
}
