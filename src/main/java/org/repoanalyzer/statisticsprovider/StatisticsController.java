package org.repoanalyzer.statisticsprovider;


import javafx.application.Application;
import javafx.stage.Stage;
import org.repoanalyzer.reporeader.IRepoReader;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.component.HeatMapComponent;
import org.repoanalyzer.statisticsprovider.component.IStatisticsComponent;
import org.repoanalyzer.statisticsprovider.view.RepoPathReaderView;

import java.util.List;
import java.util.concurrent.Future;

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
        Future<List<Commit>> commits = repoReader.getCommits();
        HeatMapComponent component = new HeatMapComponent(commits);

        try {
            component.createStatisticsView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
