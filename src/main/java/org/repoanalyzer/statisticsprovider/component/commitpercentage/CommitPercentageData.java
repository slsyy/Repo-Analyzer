package org.repoanalyzer.statisticsprovider.component.commitpercentage;


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

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getAuthorCommitsNumber() {
        return authorCommitsNumber;
    }

    public void setAuthorCommitsNumber(int authorCommitsNumber) {
        this.authorCommitsNumber = authorCommitsNumber;
    }
}
