package org.repoanalyzer.statisticsprovider.view;

import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RepoReaderProgressBarView {

    private final Stage dialogStage;
    private final ProgressBar progressBar;
    private final Label progressLabel;

    public RepoReaderProgressBarView() {
        this.dialogStage = new Stage();
        this.progressBar = new ProgressBar();
        this.progressLabel = new Label();

        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.setResizable(false);
        dialogStage.initModality(Modality.APPLICATION_MODAL);


        progressBar.setProgress(-1F);

        final Label tf = new Label("Reading repository");
        final VBox hb = new VBox();
        hb.setSpacing(5);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(tf, progressBar);

        Scene scene = new Scene(hb);
        dialogStage.setScene(scene);
    }

    public void activateProgressBar(final Task<?> task)  {
        progressBar.progressProperty().bind(task.progressProperty());
    }

    public Stage getDialogStage() {
        return dialogStage;
    }
}
