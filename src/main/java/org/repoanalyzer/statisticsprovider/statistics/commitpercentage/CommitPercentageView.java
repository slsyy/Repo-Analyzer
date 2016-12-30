package org.repoanalyzer.statisticsprovider.statistics.commitpercentage;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class CommitPercentageView {
    private List<CommitPercentageData> data;

    public CommitPercentageView(List<CommitPercentageData> data){
        this.data = data;
    }

    public void showStage(Stage stage){
        List<PieChart.Data> data1 = new ArrayList<>();
        for(CommitPercentageData d: data){
            data1.add(new PieChart.Data(d.getAuthorName(), d.getAuthorCommitsNumber()));
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(data1);
        final PieChart chart = new PieChart(pieChartData);
        chart.setMinSize(50,50);
        chart.setMaxSize(1000,1000);
        chart.setTitle("Commits");

        Scene scene = new Scene(chart,500,500, Color.WHITE);
        stage.setTitle("Commits statistic");
        stage.setScene(scene);
        stage.show();
    }
}
