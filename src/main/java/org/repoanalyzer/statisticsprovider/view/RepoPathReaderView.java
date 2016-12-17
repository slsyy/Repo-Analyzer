package org.repoanalyzer.statisticsprovider.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.repoanalyzer.statisticsprovider.StatisticsController;

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
        Button runButton = new Button("Run");
        Text introductionText = new Text("Welcome to RepoAnalyzer!\nInsert git url and click Run button :)");

        runButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                /**
                 * Pobieramy url z urlText, wysyłamy go do Reporeader'a
                 * jeśli znajdzie repo i wyciągnie statystyki to przesyła,
                 * jeśli nie to wyświetlamy okienko z błędem.
                 */
                System.out.println(urlText.getText());
                url = urlText.getText();

                if(true){
                    try {
                        controller.createStatisticsView(url);
                        // urlText.clear();
                        stage.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("This URL for git repository is wrong,\n" +
                            "or it doesn't exist.");
                    alert.showAndWait();
                }
            }
        });


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.add(introductionText,1,1);
        grid.add(urlText,1,2);
        grid.add(runButton,2,2);

        stage.setScene(new Scene(grid, 300, 150));
        stage.show();
    }
}
