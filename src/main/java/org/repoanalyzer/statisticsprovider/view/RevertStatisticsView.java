package org.repoanalyzer.statisticsprovider.view;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.repoanalyzer.statisticsprovider.data.RevertStatisticsData;

import java.util.List;

public class RevertStatisticsView {
    private List<RevertStatisticsData> statisticData;

    public RevertStatisticsView(List<RevertStatisticsData> statisticData){
        this.statisticData = statisticData;
    }

    public void createView(Stage stage){
        ObservableList<String> authorSet = FXCollections.observableArrayList();
        Spinner<String> spinner = new Spinner<>();
        for(RevertStatisticsData data : statisticData){
            authorSet.add(data.getAuthor().getFirstName());
        }
        spinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(authorSet));
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 300, 300, Color.WHITE);
        stage.setTitle("Reverts statistic per user");
        spinner.getValueFactory().setValue(authorSet.get(0));
        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
        chartData.addAll(new PieChart.Data("Revert", statisticData.get(0).getPercentage()),
                            new PieChart.Data("RestCommits", 100.0f - statisticData.get(0).getPercentage()));
        final PieChart chart = new PieChart(chartData);

        spinner.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                RevertStatisticsData data = null;
                for(RevertStatisticsData data1 : statisticData)
                    if(data1.getAuthor().getFirstName().equals(newValue)){
                        data = data1;
                        break;
                    }
                if(data== null)
                    return;
                chartData.clear();
                chartData.setAll(new PieChart.Data("Revert", data.getPercentage()),
                        new PieChart.Data("RestCommits", 100.0f -data.getPercentage()));
            }
        });

        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(5);
        gridpane.setVgap(5);
        gridpane.add(spinner,0,0);
        gridpane.add(chart,0,1);

        root.setCenter(gridpane);
        stage.setScene(scene);
        stage.show();

    }
}
