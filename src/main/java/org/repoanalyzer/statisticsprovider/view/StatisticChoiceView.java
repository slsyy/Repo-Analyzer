package org.repoanalyzer.statisticsprovider.view;


import org.repoanalyzer.statisticsprovider.statistics.IStatisticsComponent;

import java.util.List;

public class StatisticChoiceView {
    private List<IStatisticsComponent> statisticsComponents;
    private IStatisticsComponent currentStatisticsComponent;

    public StatisticChoiceView(List<IStatisticsComponent> statisticsComponents){
        this.statisticsComponents = statisticsComponents;
    }

    public IStatisticsComponent getCurrentStatisticsComponent(){
        return currentStatisticsComponent;
    }
}
