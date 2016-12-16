package org.repoanalyzer.reporeader;

public abstract class AbstractRepoReader implements IRepoReader {
    final protected String url;

    public AbstractRepoReader(String url) {
        this.url = url;
    }
}
