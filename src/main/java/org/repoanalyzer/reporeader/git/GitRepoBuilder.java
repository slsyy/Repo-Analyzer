package org.repoanalyzer.reporeader.git;

import org.repoanalyzer.reporeader.exceptions.RepositoryNotFoundOrInvalidException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;

public class GitRepoBuilder {
    private final String url;

    public GitRepoBuilder(String url) {
        this.url = url;
    }

    public Git build() throws RepositoryNotFoundOrInvalidException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        builder.setGitDir(new File(this.url))
                .setMustExist(true)
                .readEnvironment()
                .findGitDir();
        try {
            return new Git(builder.build());
        } catch (IOException exception) {
            throw new RepositoryNotFoundOrInvalidException();
        }
    }
}
