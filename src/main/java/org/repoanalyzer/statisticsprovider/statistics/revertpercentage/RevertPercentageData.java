package org.repoanalyzer.statisticsprovider.statistics.revertpercentage;


import org.repoanalyzer.reporeader.author.Author;

public class RevertPercentageData {
    private Author author;
    private Float percentage;

    public RevertPercentageData(Author author){
        this.author = author;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public Author getAuthor() {
        return author;
    }
}
