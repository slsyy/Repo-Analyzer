package org.repoanalyzer.statisticsprovider.statistics.changes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.util.List;

/**
 * Created by Jakub on 2017-01-14.
 */
public class ChangesView {
    private List<ChangesData> dataList;

    public ChangesView(List<ChangesData> dataList) {
        this.dataList = dataList;
    }

    public void showStage(Stage stage){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        GridPane checkBoxesPane = new GridPane();
        int index = 0;
        for(ChangesData d: dataList){
            PieChart.Data data1 = new PieChart.Data(d.getAuthorName(),
                    d.getChanges());
            pieChartData.add(data1);
            CheckBox checkBox = new CheckBox(d.getAuthorName());
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
        chart.setTitle("Introduced changes to project");

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
        stage.setTitle("Changes");
        stage.setScene(scene);
        stage.show();
    }
}
