package org.repoanalyzer.statisticsprovider.statistics.balanceadddelete;

import javafx.beans.property.ReadOnlyDoubleProperty;
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
import javafx.scene.paint.LinearGradient;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Comparator;
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

        GridPane listGrid = new GridPane();

        dataList.sort(new Comparator<BalanceAddDeleteData>() {
            @Override
            public int compare(BalanceAddDeleteData o1, BalanceAddDeleteData o2) {
                if(o1.getMaxFromAddedAndDeleted() >  o2.getMaxFromAddedAndDeleted())
                    return -1;
                else if(o1.getMaxFromAddedAndDeleted() <  o2.getMaxFromAddedAndDeleted())
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
//                stage.setWidth(7500*bc.getData().size()/maxSeriesPrPage);
                return new VBox();
            }
        });
        Integer pages = 0;
        Integer recordNumber = 0;
        for(BalanceAddDeleteData data : dataList){
            if(recordNumber == 0){
                TextField page = new TextField("Page "+ (pages+1));
                page.setEditable(false);
                page.setStyle("-fx-text-fill: blue;");
                listGrid.add(page,0,pages*maxSeriesPrPage + pages);
            }
            TextField name = new TextField(data.getAuthor().getFirstName());
            name.setEditable(false);
            listGrid.add(name, 0, pages*maxSeriesPrPage + pages + recordNumber + 1);
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

        Scene scene = new Scene(gp, 800, 400, Color.WHITE);
        stage.setTitle("Added and deleted lines");
        stage.setScene(scene);
        stage.show();
    }


}
