package org.repoanalyzer.statisticsprovider.statistics.commitpercentage;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class CommitPercentageView {
    private List<CommitPercentageData> data;

    public CommitPercentageView(List<CommitPercentageData> data){
        this.data = data;
    }

    public void showStage(Stage stage){
        Scene scene = new Scene(new Group());
        stage.setTitle("Commits statistic");
        stage.setWidth(500);
        stage.setHeight(500);


        List<PieChart.Data> data1 = new ArrayList<>();
        for(CommitPercentageData d: data){
            data1.add(new PieChart.Data(d.getAuthorName(), d.getAuthorCommitsNumber()));
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(data1);
        final PieChart chart = new PieChart(pieChartData);
        chart.setMaxWidth(500);
        chart.setMaxHeight(500);
        chart.setTitle("Commits");

        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();
    }
}
