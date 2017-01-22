package org.repoanalyzer.statisticsprovider.controllers;

import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import org.repoanalyzer.reporeader.IRepoReader;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.reporeader.exceptions.CannotOpenAuthorFileException;
import org.repoanalyzer.reporeader.exceptions.InvalidJsonDataFormatException;
import org.repoanalyzer.reporeader.exceptions.JsonParsingException;
import org.repoanalyzer.reporeader.exceptions.RepositoryNotFoundOrInvalidException;
import org.repoanalyzer.statisticsprovider.view.RepoReaderProgressBarView;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class RepoReaderTask extends Task<List<Commit>> {
    private  static final int REPO_READER_UPDATE_DURATION = 1;
    private IRepoReader repoReader;

    public RepoReaderTask(IRepoReader repoReader) {
        this.repoReader = repoReader;
    }

    @Override
    public List<Commit> call() throws InterruptedException, ExecutionException, RepositoryNotFoundOrInvalidException, JsonParsingException, CannotOpenAuthorFileException, InvalidJsonDataFormatException {

        Future<List<Commit>> commitsFuture;
        commitsFuture = repoReader.getCommits();

        updateProgress(repoReader.getProgress().getWorkDone(), repoReader.getProgress().getMax());

        while (!commitsFuture.isDone()) {
            updateProgress(repoReader.getProgress().getWorkDone(), repoReader.getProgress().getMax());
            Thread.sleep(REPO_READER_UPDATE_DURATION);
        }
        return commitsFuture.get();
    }

    void handleException(RepoReaderProgressBarView repoReaderProgressBarView) {
        String name = "";
        try {
            this.get();
        } catch (Exception e) {
            name = e.getMessage();
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something... something got broken and I couldn't be heard...");
        alert.setHeaderText(name);
        alert.showAndWait();
        repoReaderProgressBarView.getDialogStage().close();
    }
}
