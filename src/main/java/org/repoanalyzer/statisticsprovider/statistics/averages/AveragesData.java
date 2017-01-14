package org.repoanalyzer.statisticsprovider.statistics.averages;

/**
 * Created by Jakub on 2017-01-14.
 */
public class AveragesData {
    private String authorName;
    private Float avgAddedLines;
    private Float avgDeletedLines;
    private Float avgChangedLines;

    public AveragesData(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Float getAvgAddedLines() {
        return avgAddedLines;
    }

    public void setAvgAddedLines(Float avgAddedLines) {
        this.avgAddedLines = avgAddedLines;
    }

    public Float getAvgDeletedLines() {
        return avgDeletedLines;
    }

    public void setAvgDeletedLines(Float avgDeletedLines) {
        this.avgDeletedLines = avgDeletedLines;
    }

    public Float getAvgChangedLines() {
        return avgChangedLines;
    }

    public void setAvgChangedLines(Float avgChangedLines) {
        this.avgChangedLines = avgChangedLines;
    }

    public Float getMaxAvg(){
        return Math.max(Math.max(avgDeletedLines,avgAddedLines), Math.max(avgChangedLines,avgAddedLines));
    }
}
