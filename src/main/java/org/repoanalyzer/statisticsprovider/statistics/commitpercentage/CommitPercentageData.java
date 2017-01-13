package org.repoanalyzer.statisticsprovider.statistics.commitpercentage;


public class CommitPercentageData {
    private String authorName;
    private int authorCommitsNumber;

    public CommitPercentageData(String authorName, int authorCommitsNumber){
        this.authorName = authorName;
        this.authorCommitsNumber = authorCommitsNumber;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getAuthorCommitsNumber() {
        return authorCommitsNumber;
    }

}
