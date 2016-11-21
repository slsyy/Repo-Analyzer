package org.repoanalyzer.reporeader;

/**
 * Created by slay on 21.11.16.
 */
public abstract class AbstractRepoReader implements IRepoReader {

    public AbstractRepoReader(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    final private String url;
}
