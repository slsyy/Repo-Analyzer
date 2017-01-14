package org.repoanalyzer.statisticsprovider.statistics.commitpercentage;


import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
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
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        GridPane checkBoxesPane = new GridPane();
        int index = 0;
        for(CommitPercentageData d: data){
            PieChart.Data data1 = new PieChart.Data(d.getAuthorName()+ " (" + d.getAuthorCommitsNumber() + ")",
                    d.getAuthorCommitsNumber());
            pieChartData.add(data1);
            CheckBox checkBox = new CheckBox(d.getAuthorName()+ " (" + d.getAuthorCommitsNumber() + ")");
            checkBox.setSelected(true);
            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    try {
                        checkBox.setSelected(newValue);
                        if(newValue){
                            pieChartData.add(data1);
                        }else {
                            pieChartData.remove(data1);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            checkBoxesPane.add(checkBox,0,index);
            index++;
        }
        final PieChart chart = new PieChart(pieChartData);

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


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(checkBoxesPane);
        gridPane.add(scrollPane, 1, 0);

        Scene scene = new Scene(gridPane,700,500, Color.WHITE);
        stage.setTitle("Commits statistic");
        stage.setScene(scene);
        stage.show();
    }
}
