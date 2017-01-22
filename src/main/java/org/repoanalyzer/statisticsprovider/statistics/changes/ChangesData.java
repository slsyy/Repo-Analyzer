package org.repoanalyzer.statisticsprovider.statistics.changes;

import org.repoanalyzer.reporeader.author.Author;

/**
 * Created by Jakub on 2017-01-14.
 */
public class ChangesData {
    private String authorName;
    private int changes;

    public ChangesData(String authorName) {
        this.authorName = authorName;
        changes = 0;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Integer getChanges() {
        return changes;
    }

    public void incrementChanges(Integer changes) {
        this.changes += changes;
    }
}
