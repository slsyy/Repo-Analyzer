package org.repoanalyzer.statisticsprovider.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

/**
 * Created by Jakub on 2016-11-26.
 */
public class CommitStatisticStage {
    public void showStage(Stage stage) throws Exception {
        Scene scene = new Scene(new Group());
        stage.setTitle("Commits statistic");
        stage.setWidth(400);
        stage.setHeight(400);

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("mateo123", 13),
                        new PieChart.Data("cris123", 25),
                        new PieChart.Data("pablo123", 10),
                        new PieChart.Data("jacob123", 22),
                        new PieChart.Data("paolo123", 30));
        final PieChart chart = new PieChart(pieChartData);
        chart.setMaxWidth(350);
        chart.setMaxHeight(350);
        chart.setTitle("Commits");

        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();
    }

}
