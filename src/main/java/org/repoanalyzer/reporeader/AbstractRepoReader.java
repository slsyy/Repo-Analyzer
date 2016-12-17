package org.repoanalyzer.reporeader;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractRepoReader implements IRepoReader {
    final protected String url;
    protected char state;
    protected int size;
    protected AtomicInteger progress;

    public AbstractRepoReader(String url) {
        this.url = url;
    }
}
