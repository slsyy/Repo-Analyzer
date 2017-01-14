package org.repoanalyzer.statisticsprovider.statistics.averages;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Jakub on 2017-01-14.
 */
public class AveragesView {
    private List<AveragesData> dataList;
    private final Integer maxSeriesPrPage = 20;

    public AveragesView(List<AveragesData> dataList) {
        this.dataList = dataList;
    }

    public void showStage(Stage stage){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
                new BarChart<>(xAxis,yAxis);
        xAxis.setLabel("Author");
        yAxis.setLabel("Value");

        GridPane listGrid = new GridPane();

        dataList.sort(new Comparator<AveragesData>() {
            @Override
            public int compare(AveragesData o1, AveragesData o2) {
                if(o1.getMaxAvg() >  o2.getMaxAvg())
                    return -1;
                else if(o1.getMaxAvg() <  o2.getMaxAvg())
                    return 1;
                else
                    return 0;
            }
        });

        Pagination pagination = new Pagination(dataList.size()/maxSeriesPrPage + 1);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer param) {
                bc.getData().clear();
                bc.getData().retainAll();

                XYChart.Series addSerie = new XYChart.Series();
                addSerie.setName("Avg. of added lines");
                XYChart.Series deleteSerie = new XYChart.Series();
                deleteSerie.setName("Avg. of deleted lines");
                XYChart.Series changeSerie = new XYChart.Series();
                changeSerie.setName("Avg. of changed lines");
                int i = 0;
                while(i<maxSeriesPrPage && dataList.size() > i+ param*maxSeriesPrPage) {
                    addSerie.getData().add(new XYChart.Data(dataList.get(param*maxSeriesPrPage + i).getAuthorName(), dataList.get(param*maxSeriesPrPage + i).getAvgAddedLines()));
                    deleteSerie.getData().add(new XYChart.Data(dataList.get(param*maxSeriesPrPage + i).getAuthorName(), dataList.get(param*maxSeriesPrPage + i).getAvgDeletedLines()));
                    changeSerie.getData().add(new XYChart.Data(dataList.get(param*maxSeriesPrPage + i).getAuthorName(), dataList.get(param*maxSeriesPrPage + i).getAvgChangedLines()));
                    i++;
                }
                bc.getData().addAll(addSerie,deleteSerie,changeSerie);
                stage.setWidth(5000*bc.getData().size()/maxSeriesPrPage);
                return new VBox();
            }
        });

        Integer pages = 0;
        Integer recordNumber = 0;
        for(AveragesData data : dataList){
            if(recordNumber == 0){
                TextField page = new TextField("Page "+ (pages+1));
                page.setStyle("-fx-text-fill: blue;");
                listGrid.add(page,0,pages*maxSeriesPrPage + pages);
            }
            listGrid.add(new TextField(data.getAuthorName())
                    , 0, pages*maxSeriesPrPage + pages + recordNumber + 1);
            recordNumber++;
            if(recordNumber.equals(maxSeriesPrPage)){
                recordNumber = 0;
                pages++;
            }
        }

        GridPane gp = new GridPane();
        gp.add(bc,0,0);
        gp.add(pagination,0,1);
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);
        RowConstraints rc = new RowConstraints();
        rc.setVgrow(Priority.ALWAYS);
        gp.getColumnConstraints().add(cc);
        gp.getRowConstraints().add(rc);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(listGrid);
        gp.add(scrollPane, 1, 0);

        Scene scene = new Scene(gp, 700, 400, Color.WHITE);
        stage.setTitle("Averages of added, deleted and changed lines per commit");
        stage.setScene(scene);
        stage.show();
    }
}
