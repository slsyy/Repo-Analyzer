package org.repoanalyzer.reporeader;

import org.repoanalyzer.reporeader.commit.Author;
import org.repoanalyzer.reporeader.commit.AuthorProvider;
import org.repoanalyzer.reporeader.commit.FilePreloadedAuthorProvider;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractRepoReader implements IRepoReader {
    final protected String url;
    final protected String authorFile;
    protected AuthorProvider authorProvider;
    protected int size;
    protected AtomicInteger progress;

    public AbstractRepoReader(String url) {
        this(url,"");
    }

    public AbstractRepoReader(String url, String authorFile) {
        this.url = url;
        this.authorFile = authorFile;
        this.progress = new AtomicInteger();
        this.size = 0;
    }

    public Progress getProgress() {
        return new Progress(this.progress.get(), this.size);
    }

    public Set<Author> getAuthors() {
        return this.authorProvider.getAuthors();
    }

    protected void prepareAuthorProvider() {
        try {
            this.authorProvider = new FilePreloadedAuthorProvider(this.authorFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Ignoring provided file with authors.");
            this.authorProvider = new AuthorProvider();
        }
    }
}
