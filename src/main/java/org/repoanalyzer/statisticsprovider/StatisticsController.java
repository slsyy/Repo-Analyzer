package org.repoanalyzer.statisticsprovider;


import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.repoanalyzer.reporeader.IRepoReader;
import org.repoanalyzer.reporeader.RepoReaderFactory;
import org.repoanalyzer.reporeader.commit.Author;
import org.repoanalyzer.reporeader.exceptions.CannotOpenAuthorFileException;
import org.repoanalyzer.reporeader.exceptions.InvalidJsonDataFormatException;
import org.repoanalyzer.reporeader.exceptions.JsonParsingException;
import org.repoanalyzer.reporeader.exceptions.RepositoryNotFoundOrInvalidException;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.statistics.*;
import org.repoanalyzer.statisticsprovider.statistics.averages.AveragesComponent;
import org.repoanalyzer.statisticsprovider.statistics.balanceadddelete.BalanceAddDeleteComponent;
import org.repoanalyzer.statisticsprovider.statistics.changes.ChangesComponent;
import org.repoanalyzer.statisticsprovider.statistics.commitpercentage.CommitPercentageComponent;
import org.repoanalyzer.statisticsprovider.statistics.commitsperhour.CommitsPerHourComponent;
import org.repoanalyzer.statisticsprovider.statistics.revertpercentage.RevertPercentageComponent;
import org.repoanalyzer.statisticsprovider.view.RepoPathReaderView;
import org.repoanalyzer.statisticsprovider.view.RepoReaderProgressBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class StatisticsController extends Application {
    public static final int REPO_READER_UPDATE_DURATION = 1;
    private IRepoReader repoReader;

    public void startApplication(){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new RepoPathReaderView(this).showStage(new Stage());
    }

    public void createStatisticsView(String url, String authorFile){
        repoReader = RepoReaderFactory.create(url, authorFile);

        Task<List<Commit>> task = new Task<List<Commit>>() {
            @Override
            public List<Commit> call() throws InterruptedException, ExecutionException, RepositoryNotFoundOrInvalidException, JsonParsingException, CannotOpenAuthorFileException, InvalidJsonDataFormatException {

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
            String name = "";
            try {
                task.get();
            } catch (Exception e) {
                name = e.getMessage();
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Something... something got broken and I couldn't be heard...");
            alert.setHeaderText(name);
            alert.showAndWait();
            repoReaderProgressBarView.getDialogStage().close();
        });

        repoReaderProgressBarView.getDialogStage().show();

        new Thread(task).start();
    }

    private void createStatisticsComponents(Task<List<Commit>> task) {
        List<Commit> commits = null;
        Set<Author> authors = repoReader.getAuthors();
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
