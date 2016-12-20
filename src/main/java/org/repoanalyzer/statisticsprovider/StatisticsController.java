package org.repoanalyzer.statisticsprovider;


import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import org.repoanalyzer.reporeader.IRepoReader;
import org.repoanalyzer.reporeader.RepoReaderFactory;
import org.repoanalyzer.reporeader.exceptions.RepositoryNotFoundOrInvalidException;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.statistics.*;
import org.repoanalyzer.statisticsprovider.statistics.balanceadddelete.BalanceAddDeleteComponent;
import org.repoanalyzer.statisticsprovider.statistics.commitpercentage.CommitPercentageComponent;
import org.repoanalyzer.statisticsprovider.statistics.commitsperhour.CommitsPerHourComponent;
import org.repoanalyzer.statisticsprovider.statistics.revertpercentage.RevertPercentageComponent;
import org.repoanalyzer.statisticsprovider.view.RepoPathReaderView;
import org.repoanalyzer.statisticsprovider.view.RepoReaderProgressBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class StatisticsController extends Application {
    public static final int REPO_READER_UPDATE_DURATION = 1;
    private IRepoReader repoReader;
    //private List<IStatisticsComponent> statisticsComponents;
    //private IStatisticsComponent currentStatisticsComponent;

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
            public List<Commit> call() throws InterruptedException, ExecutionException, RepositoryNotFoundOrInvalidException {

                Future<List<Commit>> commitsFuture = null;
                commitsFuture = repoReader.getCommits();

                updateProgress(repoReader.getProgress().getWorkDone(), repoReader.getProgress().getMax());

                while (!commitsFuture.isDone()) {
                    updateProgress(repoReader.getProgress().getWorkDone(), repoReader.getProgress().getMax());
                    Thread.sleep(REPO_READER_UPDATE_DURATION);
                }
                return commitsFuture.get();
            }
        };

        RepoReaderProgressBarView repoReaderProgressBarView = new RepoReaderProgressBarView();
        repoReaderProgressBarView.activateProgressBar(task);


        task.setOnSucceeded(workerStateEvent -> {
            repoReaderProgressBarView.getDialogStage().close();
            createStatisticsComponents(task);
        });

        task.setOnFailed(workerStateEvent -> {
            repoReaderProgressBarView.getDialogStage().close();
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
            return;
        }

        List<IStatisticsComponent> statisticsComponents = new ArrayList<>();
        statisticsComponents.add(new CommitsPerHourComponent(commits));
        statisticsComponents.add(new CommitPercentageComponent(commits));
        statisticsComponents.add(new BalanceAddDeleteComponent(commits));
        statisticsComponents.add(new RevertPercentageComponent(commits));

        statisticsComponents.forEach(IStatisticsComponent::createAndShowStatisticsView);

    }
}
