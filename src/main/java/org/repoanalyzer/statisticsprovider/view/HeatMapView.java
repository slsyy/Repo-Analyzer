package org.repoanalyzer.statisticsprovider.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.repoanalyzer.statisticsprovider.data.Days;
import org.repoanalyzer.statisticsprovider.data.HeatMapData;

import java.util.List;

/**
 * Created by Jakub on 2016-12-15.
 */
public class HeatMapView {

    private List<HeatMapData> data;
    private final Integer numOfCommits;
    private final Color OVER_10 = Color.web("#ff0000");
    private final Color OVER_7 = Color.web("#cc3333");
    private final Color OVER_3 = Color.web("#b34d4d");
    private final Color OVER_0 = Color.web("#996666");
    private final Color ZERO = Color.web("#808080");


    public HeatMapView(List<HeatMapData> data, Integer numOfCommits) {
        this.data = data;
        this.numOfCommits = numOfCommits;
    }

    public void showStage(Stage stage){
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 380, 150, Color.WHITE);

        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(5);
        gridpane.setVgap(5);


        for(Days day : Days.values())
            gridpane.add(new Text(day.getShortcut()),0,day.getNumber()+1);
        for(Integer i = 0; i < 24; i++)
            gridpane.add(new Text(i.toString()),i+1,0);

        for(HeatMapData data1 : data){
            Rectangle rect = new Rectangle(20,20);
            rect.setFill(chooseColor(data1.getNumOfCommits()));
            gridpane.add(rect,data1.getHour()+1, data1.getDay().getNumber()+1);
        }

        showDesription(gridpane);

        root.setCenter(gridpane);
        stage.setWidth(800);
        stage.setHeight(300);
        stage.setScene(scene);
        stage.show();
    }

    private Color chooseColor(final Integer commits){
        if(commits == 0)
            return ZERO;
        Float percentage = commits/numOfCommits *100f;
        if(percentage>10)
            return OVER_10;
        else if(percentage>7)
            return OVER_7;
        else if(percentage>3)
            return OVER_3;
        else
            return OVER_0;
    }

    private void showDesription(GridPane gridPane){
        Rectangle rect = new Rectangle(20,20);
        rect.setFill(OVER_10);
        gridPane.add(rect,28, 2);
        gridPane.add(new Text(">10%"),29,2);

        Rectangle rect1 = new Rectangle(20,20);
        rect1.setFill(OVER_7);
        gridPane.add(rect1,28, 3);
        gridPane.add(new Text(">7%"),29,3);

        Rectangle rect2 = new Rectangle(20,20);
        rect2.setFill(OVER_3);
        gridPane.add(rect2,28, 4);
        gridPane.add(new Text(">3%"),29,4);

        Rectangle rect3 = new Rectangle(20,20);
        rect3.setFill(OVER_0);
        gridPane.add(rect3,28, 5);
        gridPane.add(new Text(">0%"),29,5);

        Rectangle rect4 = new Rectangle(20,20);
        rect4.setFill(ZERO);
        gridPane.add(rect4,28, 6);
        gridPane.add(new Text("0%"),29,6);

    }
}

