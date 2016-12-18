package org.repoanalyzer.statisticsprovider;


import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import org.repoanalyzer.reporeader.IRepoReader;
import org.repoanalyzer.reporeader.RepoReaderFactory;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.calculator.BalanceAddDeleteCalculator;
import org.repoanalyzer.statisticsprovider.component.*;
import org.repoanalyzer.statisticsprovider.data.RevertStatisticsData;
import org.repoanalyzer.statisticsprovider.view.RepoPathReaderView;
import org.repoanalyzer.statisticsprovider.view.RepoReaderProgressBarView;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class StatisticsController extends Application {
    public static final int REPO_READER_UPDATE_DURATION = 1;
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
        repoReader = RepoReaderFactory.create(url);

        Task<List<Commit>> task = new Task<List<Commit>>() {
            @Override
            public List<Commit> call() throws InterruptedException {
                Future<List<Commit>> commitsFuture = repoReader.getCommits();

                updateProgress(repoReader.getProgress().getWorkDone(), repoReader.getProgress().getMax());

                while (!commitsFuture.isDone()) {
                    updateProgress(repoReader.getProgress().getWorkDone(), repoReader.getProgress().getMax());
                    Thread.sleep(REPO_READER_UPDATE_DURATION);
                }

                try {
                    return commitsFuture.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    return null;
                }

            }
        };

        RepoReaderProgressBarView repoReaderProgressBarView = new RepoReaderProgressBarView();
        repoReaderProgressBarView.activateProgressBar(task);


        task.setOnSucceeded(event -> {
            repoReaderProgressBarView.getDialogStage().close();
            createStatisticsComponents(task);
        });

        repoReaderProgressBarView.getDialogStage().show();

        Thread thread = new Thread(task);
        thread.start();
    }

    private void createStatisticsComponents(Task<List<Commit>> task) {
        List<Commit> commits = null;
        try {
            commits = task.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        HeatMapComponent heatMapComponent = new HeatMapComponent(commits);
        CommitPercentageComponent commitPercentageComponent = new CommitPercentageComponent(commits);
        BalanceAddDeleteComponent balanceAddDeleteComponent = new BalanceAddDeleteComponent(commits);
        RevertStatisticsComponent revertStatisticsComponent = new RevertStatisticsComponent(commits);

        try {
            heatMapComponent.createStatisticsView();
            commitPercentageComponent.createStatisticsView();
            balanceAddDeleteComponent.createStatisticsView();
            revertStatisticsComponent.createStatisticsView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
