package org.repoanalyzer.statisticsprovider.statistics;

import org.repoanalyzer.reporeader.commit.Commit;

import java.util.List;

public abstract class AbstractStatisticsComponent implements IStatisticsComponent{
    protected List<Commit> commits;

    public AbstractStatisticsComponent(List<Commit> commits){
        this.commits = commits;
    }

}
