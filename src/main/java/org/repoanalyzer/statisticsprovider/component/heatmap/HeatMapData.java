package org.repoanalyzer.statisticsprovider.component.heatmap;

/**
 * Created by Jakub on 2016-12-15.
 */
public class HeatMapData {
    private Days day;
    private Integer hour;
    private Integer numOfCommits = 0;

    public HeatMapData(Days day, Integer hour) {
        this.day = day;
        this.hour = hour;
    }

    public Integer getNumOfCommits() {
        return numOfCommits;
    }

    public void incrementCommits(){
        numOfCommits++;
    }

    public Days getDay() {
        return day;
    }

    public Integer getHour() {
        return hour;
    }
}
