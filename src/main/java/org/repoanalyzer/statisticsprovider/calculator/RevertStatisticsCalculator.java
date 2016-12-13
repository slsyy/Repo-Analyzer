package org.repoanalyzer.statisticsprovider.calculator;


import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.data.RevertStatisticsData;

import java.util.List;

public class RevertStatisticsCalculator {
    private List<Commit> commits;

    public RevertStatisticsCalculator(List<Commit> commits){
        this.commits = commits;
    }

    public RevertStatisticsData generateStatistics(){
        // todo
        return null;
    }
}
