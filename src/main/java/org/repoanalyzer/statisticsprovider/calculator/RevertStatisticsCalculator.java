package org.repoanalyzer.statisticsprovider.calculator;


import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.data.RevertStatisticsData;

import java.util.List;
import java.util.concurrent.Future;

public class RevertStatisticsCalculator {
    private Future<List<Commit>> commits;

    public RevertStatisticsCalculator(Future<List<Commit>> commits){
        this.commits = commits;
    }

    public RevertStatisticsData generateStatistics(){
        // todo
        return null;
    }
}
