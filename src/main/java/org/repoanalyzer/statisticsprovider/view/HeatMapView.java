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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jakub on 2016-12-15.
 */
public class HeatMapView {

    private List<HeatMapData> data;
    private Integer numOfCommits;



    public HeatMapView(List<HeatMapData> data, Integer numOfCommits) {
        this.data = data;
        this.numOfCommits = numOfCommits;
    }

    public void showStage(){
        Stage stage = new Stage();
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

        root.setCenter(gridpane);
        stage.setWidth(1000);
        stage.setHeight(300);
        stage.setScene(scene);
        stage.show();
    }

    private Color chooseColor(Integer commits){
        //TODO: more colors
        if(numOfCommits/commits>30)
            return Color.RED;
        else
            return Color.GREEN;
    }
}

