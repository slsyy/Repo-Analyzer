package org.repoanalyzer.statisticsprovider.statistics.balanceadddelete;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

/**
 * Created by Jakub on 2016-12-18.
 */
public class BalanceAddDeleteView {

    private List<BalanceAddDeleteData> dataList;

    public BalanceAddDeleteView(List<BalanceAddDeleteData> data) {
        this.dataList = data;
    }

    public void showStage(Stage stage){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
                new BarChart<>(xAxis,yAxis);
        xAxis.setLabel("Author");
        yAxis.setLabel("Value");

        XYChart.Series addSerie = new XYChart.Series();
        addSerie.setName("Added lines");
        XYChart.Series deleteSerie = new XYChart.Series();
        deleteSerie.setName("Deleted lines");

        for(BalanceAddDeleteData data : dataList){
            addSerie.getData().add(new XYChart.Data(data.getAuthor().getFirstName(), data.getAddedLines()));
            deleteSerie.getData().add(new XYChart.Data(data.getAuthor().getFirstName(), data.getDeletedLines()));
        }

        Scene scene = new Scene(bc, 500, 400, Color.WHITE);
        bc.getData().addAll(addSerie,deleteSerie);
        stage.setScene(scene);
        stage.show();
    }
}
