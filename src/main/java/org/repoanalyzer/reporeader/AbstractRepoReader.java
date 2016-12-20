package org.repoanalyzer.reporeader;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractRepoReader implements IRepoReader {
    final protected String url;
    final protected String authorFile;
    protected int size;
    protected AtomicInteger progress;

    public AbstractRepoReader(String url) {
        this(url,"");
    }

    public AbstractRepoReader(String url, String authorFile) {
        this.url = url;
        this.authorFile = authorFile;
    }
}
