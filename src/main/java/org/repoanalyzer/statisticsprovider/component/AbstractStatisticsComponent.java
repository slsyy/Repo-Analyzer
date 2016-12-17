package org.repoanalyzer.statisticsprovider.component;

import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.view.HeatMapView;

import java.util.List;
import java.util.concurrent.Future;

public abstract class AbstractStatisticsComponent implements IStatisticsComponent{
    protected Future<List<Commit>> commits;

    public AbstractStatisticsComponent(Future<List<Commit>> commits){
        this.commits = commits;
    }

}
