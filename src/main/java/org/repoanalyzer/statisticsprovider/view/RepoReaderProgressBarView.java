package org.repoanalyzer.statisticsprovider.view;

import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RepoReaderProgressBarView {

    private final Stage dialogStage;
    private final ProgressIndicator progressIndicator;

    public RepoReaderProgressBarView() {
        this.dialogStage = new Stage();
        this.progressIndicator = new ProgressIndicator();

        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.setResizable(false);
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        progressIndicator.setProgress(-1F);

        final Label readingRepositoryLabel = new Label("Reading repository");
        final VBox vb = new VBox(15);
        vb.setPadding(new Insets(25, 25, 25, 25));
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(readingRepositoryLabel, progressIndicator);

        Scene scene = new Scene(vb);
        dialogStage.setScene(scene);
    }

    public void activateProgressBar(final Task<?> task)  {
        progressIndicator.progressProperty().bind(task.progressProperty());
    }

    public Stage getDialogStage() {
        return dialogStage;
    }
}
