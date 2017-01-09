package org.repoanalyzer.statisticsprovider.statistics.commitpercentage;


import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
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


        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), "(", data.pieValueProperty().longValue(), ")"
                        )
                )
        );

        chart.setMinSize(50,50);
        chart.setMaxSize(1000,1000);
        chart.setTitle("Commits");

        GridPane gridPane = new GridPane();
        gridPane.add(chart, 0, 0);
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);
        RowConstraints rc = new RowConstraints();
        rc.setVgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().add(cc);
        gridPane.getRowConstraints().add(rc);



        Scene scene = new Scene(gridPane,500,500, Color.WHITE);
        stage.setTitle("Commits statistic");
        stage.setScene(scene);
        stage.show();
    }
}
