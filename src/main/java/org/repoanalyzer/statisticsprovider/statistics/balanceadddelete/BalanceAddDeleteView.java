package org.repoanalyzer.statisticsprovider.statistics.balanceadddelete;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Pagination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.List;

/**
 * Created by Jakub on 2016-12-18.
 */
public class BalanceAddDeleteView {

    private List<BalanceAddDeleteData> dataList;
    private final Integer maxSeriesPrPage = 20;

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

        Pagination pagination = new Pagination(dataList.size()/maxSeriesPrPage + 1);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer param) {
                bc.getData().clear();
                bc.getData().retainAll();

                XYChart.Series addSerie = new XYChart.Series();
                addSerie.setName("Added lines");
                XYChart.Series deleteSerie = new XYChart.Series();
                deleteSerie.setName("Deleted lines");
                int i = 0;
                while(i<maxSeriesPrPage && dataList.size() > i+ param*maxSeriesPrPage) {
                    addSerie.getData().add(new XYChart.Data(dataList.get(param*maxSeriesPrPage + i).getAuthor().getFirstName(), dataList.get(param*maxSeriesPrPage + i).getAddedLines()));
                    deleteSerie.getData().add(new XYChart.Data(dataList.get(param*maxSeriesPrPage + i).getAuthor().getFirstName(), dataList.get(param*maxSeriesPrPage + i).getDeletedLines()));
                    i++;
                }
                bc.getData().addAll(addSerie,deleteSerie);
                stage.setWidth(4000*bc.getData().size()/maxSeriesPrPage);
                return new VBox();
            }
        });

        GridPane gp = new GridPane();
        gp.add(bc,0,0);
        gp.add(pagination,0,1);

        Scene scene = new Scene(gp, 700, 400, Color.WHITE);
        stage.setTitle("Added and deleted lines");
        stage.setScene(scene);
        stage.show();
    }


}
