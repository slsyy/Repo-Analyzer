package org.repoanalyzer.statisticsprovider;


import javafx.application.Application;
import javafx.stage.Stage;
import org.repoanalyzer.reporeader.IRepoReader;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.component.CommitPercentageComponent;
import org.repoanalyzer.statisticsprovider.component.HeatMapComponent;
import org.repoanalyzer.statisticsprovider.component.IStatisticsComponent;
import org.repoanalyzer.statisticsprovider.view.RepoPathReaderView;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class StatisticsController extends Application {
    private IRepoReader repoReader;
    private List<IStatisticsComponent> statisticsComponents;
    private IStatisticsComponent currentStatisticsComponent;

    public void startApplication(){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new RepoPathReaderView(this).showStage(new Stage());
    }

    public void createStatisticsView(String url){
        repoReader = new MockedRepoReader(url);
        List<Commit> commits = null;
        try {
            commits = repoReader.getCommits().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        HeatMapComponent heatMapComponent = new HeatMapComponent(commits);
        CommitPercentageComponent commitPercentageComponent = new CommitPercentageComponent(commits);

        try {
            heatMapComponent.createStatisticsView();
            commitPercentageComponent.createStatisticsView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
