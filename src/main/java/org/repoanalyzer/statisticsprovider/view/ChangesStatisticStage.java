package org.repoanalyzer.statisticsprovider.view;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Jakub on 2016-11-26.
 */
public class ChangesStatisticStage {
    public void showStage(Stage stage) {
        stage.setTitle("Changes statistic");

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Users");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of \nmodificated lines");

        BarChart barChart = new BarChart(xAxis, yAxis);

        XYChart.Series dataSeries1 = new XYChart.Series();
//        dataSeries1.setName("2014");

        dataSeries1.getData().add(new XYChart.Data("paolo123", 567));
        dataSeries1.getData().add(new XYChart.Data("pablo123", 300));
        dataSeries1.getData().add(new XYChart.Data("jacob123", 233));
        dataSeries1.getData().add(new XYChart.Data("mateo123", 654));
        dataSeries1.getData().add(new XYChart.Data("cris123", 432));

        barChart.getData().add(dataSeries1);

        VBox vbox = new VBox(barChart);

        Scene scene = new Scene(vbox, 400, 200);

        stage.setScene(scene);
        stage.setHeight(300);
        stage.setWidth(500);

        stage.show();
    }
}
