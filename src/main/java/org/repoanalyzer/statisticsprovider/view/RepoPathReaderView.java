package org.repoanalyzer.statisticsprovider.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.repoanalyzer.statisticsprovider.StatisticsController;

import java.io.File;

public class RepoPathReaderView {
    private String url;
    private StatisticsController controller;

    public RepoPathReaderView(StatisticsController controller){
        this.controller = controller;
    }

    public void showStage(Stage stage){
        stage.setTitle("RepoAnalyzer");

        final TextField urlText = new TextField();
        urlText.setPromptText("Insert git url...");
        urlText.setFocusTraversable(false);

        final Text introductionText = new Text("Welcome to RepoAnalyzer!\nInsert git url and click Run button :)");


        Button browseButton = new Button("Browse");
        Button runButton = new Button("Run");

        browseButton.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory =
                    directoryChooser.showDialog(stage);
            urlText.setText(selectedDirectory.getAbsolutePath());
        });

        runButton.setOnAction(event -> {
            url = urlText.getText();
            try {
                controller.createStatisticsView(url);
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.add(introductionText,1,1);
        grid.add(urlText,1,2);
        grid.add(browseButton,2,2);
        grid.add(runButton,1,3);

        stage.setScene(new Scene(grid, 400, 150));
        stage.show();
    }
}
