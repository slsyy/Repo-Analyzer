package org.repoanalyzer.statisticsprovider.controllers;


import javafx.concurrent.Task;
import org.repoanalyzer.reporeader.author.Author;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.statistics.IStatisticsComponent;
import org.repoanalyzer.statisticsprovider.statistics.averages.AveragesComponent;
import org.repoanalyzer.statisticsprovider.statistics.balanceadddelete.BalanceAddDeleteComponent;
import org.repoanalyzer.statisticsprovider.statistics.changes.ChangesComponent;
import org.repoanalyzer.statisticsprovider.statistics.commitpercentage.CommitPercentageComponent;
import org.repoanalyzer.statisticsprovider.statistics.commitsperhour.CommitsPerHourComponent;
import org.repoanalyzer.statisticsprovider.statistics.revertpercentage.RevertPercentageComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class StatisticComponentsCreator {
    public void createStatisticsComponents(Task<List<Commit>> task, Set<Author> authors) {
        List<Commit> commits = null;
        try {
            commits = task.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        List<IStatisticsComponent> statisticsComponents = new ArrayList<>();
        statisticsComponents.add(new CommitsPerHourComponent(commits));
        statisticsComponents.add(new CommitPercentageComponent(authors));
        statisticsComponents.add(new BalanceAddDeleteComponent(authors));
        statisticsComponents.add(new RevertPercentageComponent(authors));
        statisticsComponents.add(new AveragesComponent(authors));
        statisticsComponents.add(new ChangesComponent(authors));

        statisticsComponents.forEach(IStatisticsComponent::createAndShowStatisticsView);

    }
}
