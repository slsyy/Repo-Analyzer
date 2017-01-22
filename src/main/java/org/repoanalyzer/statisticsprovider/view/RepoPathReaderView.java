package org.repoanalyzer.statisticsprovider.view;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.repoanalyzer.statisticsprovider.StatisticsController;

import java.io.File;

public class RepoPathReaderView {
    public static final int LONG_FIELD_WIDTH = 500;
    private StatisticsController controller;

    public RepoPathReaderView(StatisticsController controller){
        this.controller = controller;
    }

    public void showStage(Stage stage){
        stage.setTitle("RepoAnalyzer");

        final Text introductionText = new Text("Welcome to RepoAnalyzer!\nInsert git url and click Run button :)");

        final TextField urlText = new TextField();
        urlText.setPromptText("Insert repository url...");
        urlText.setFocusTraversable(false);

        final TextField authorListText = new TextField();
        authorListText.setPromptText("Insert author file path... (optional)");
        authorListText.setFocusTraversable(false);


        Button urlBrowseButton = new Button("Browse");
        Button authorListBrowseButton = new Button("Browse");

        Button runButton = new Button("Run");

        urlBrowseButton.setOnAction((ActionEvent event) -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            try {
                File selectedDirectory =
                        directoryChooser.showDialog(stage);
                urlText.setText(selectedDirectory.getAbsolutePath());
            } catch (Exception ignored) {}
        });

        authorListBrowseButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            try {
                File selectedFile = fileChooser.showOpenDialog(stage);
                authorListText.setText(selectedFile.getAbsolutePath());
            } catch (Exception ignored) {}
        });


        runButton.setOnAction(event -> {
            String url = urlText.getText();
            String authorFile = authorListText.getText();

            try {
                controller.createStatisticsView(url, authorFile);
                stage.close();
            } catch (Exception e) {
                stage.close();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Something... something got broken and I couldn't be heard...");
                alert.setHeaderText(e.getMessage());
                alert.showAndWait();
            }
        });

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.add(introductionText,0,0, 2, 1);
        grid.setHgap(10);
        grid.setVgap(10);

        GridPane.setHalignment(urlText, HPos.RIGHT);
        urlText.setPrefWidth(LONG_FIELD_WIDTH);
        grid.add(urlText,0,1);

        GridPane.setHalignment(urlBrowseButton, HPos.LEFT);
        grid.add(urlBrowseButton,1,1);

        GridPane.setHalignment(urlText, HPos.RIGHT);
        authorListText.setPrefWidth(LONG_FIELD_WIDTH);
        grid.add(authorListText,0,2);

        GridPane.setHalignment(urlBrowseButton, HPos.LEFT);
        grid.add(authorListBrowseButton,1,2);

        runButton.setAlignment(Pos.CENTER);
        runButton.setPrefWidth(LONG_FIELD_WIDTH);
        grid.add(runButton,0,3);


        stage.setScene(new Scene(grid, 600, 225));
        stage.show();
    }
}
