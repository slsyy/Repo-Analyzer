package org.repoanalyzer.statisticsprovider.statistics.revertpercentage;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

public class RevertPercentageView {
    private List<RevertPercentageData> statisticData;

    public RevertPercentageView(List<RevertPercentageData> statisticData){
        this.statisticData = statisticData;
    }

    public void createView(Stage stage){
        ObservableList<String> authorSet = FXCollections.observableArrayList();
        Spinner<String> spinner = new Spinner<>();
        for(RevertPercentageData data : statisticData){
            authorSet.add(data.getAuthor().getFirstName());
        }
        spinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(authorSet));

        stage.setTitle("Reverts statistic per user");
        spinner.getValueFactory().setValue(authorSet.get(0));
        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
        chartData.addAll(new PieChart.Data("Revert", statisticData.get(0).getPercentage()),
                            new PieChart.Data("RestCommits", 100.0f - statisticData.get(0).getPercentage()));
        final PieChart chart = new PieChart(chartData);
        chart.setMinSize(50,50);
        chart.setMaxSize(1000,1000);

        spinner.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                RevertPercentageData data = null;
                for(RevertPercentageData data1 : statisticData)
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
        Scene scene = new Scene(gridpane, 400, 400, Color.WHITE);
        stage.setScene(scene);
        stage.show();

    }
}
