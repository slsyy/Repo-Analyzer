package org.repoanalyzer.statisticsprovider;


import org.repoanalyzer.reporeader.IRepoReader;
import org.repoanalyzer.statisticsprovider.component.IStatisticsComponent;

import java.util.List;

public class StatisticsController {
    private IRepoReader repoReader;
    private List<IStatisticsComponent> statisticsComponents;
    private IStatisticsComponent currentStatisticsComponent;

    public void createStatisticsView(){
        // todo
    }
}
