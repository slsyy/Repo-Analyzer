package org.repoanalyzer.statisticsprovider.statistics.changes;

/**
 * Created by Jakub on 2017-01-14.
 */
public class ChangesData {
    private String authorName;
    private Integer changes;

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
