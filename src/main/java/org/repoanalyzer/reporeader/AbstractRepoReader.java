package org.repoanalyzer.reporeader;

import org.repoanalyzer.reporeader.author.Author;
import org.repoanalyzer.reporeader.author.AuthorProvider;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractRepoReader implements IRepoReader {
    final protected String url;
    final protected AuthorProvider authorProvider;
    protected int size;
    protected AtomicInteger progress;

    public AbstractRepoReader(String url, AuthorProvider authorProvider) {
        this.url = url;
        this.authorProvider = authorProvider;
        this.size = 0;
        this.progress = new AtomicInteger();
    }

    public Progress getProgress() {
        return new Progress(this.progress.get(), this.size);
    }

    public Set<Author> getAuthors() {
        return this.authorProvider.getAuthors();
    }
}
