package org.repoanalyzer.statisticsprovider.data;


import org.repoanalyzer.reporeader.commit.Author;
import org.repoanalyzer.reporeader.commit.Commit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RevertStatisticsData {
    private Author author;
    private Float percentage;

    public RevertStatisticsData(Author author){
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
