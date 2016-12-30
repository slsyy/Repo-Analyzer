package org.repoanalyzer.statisticsprovider.statistics.commitsperhour;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Jakub on 2016-12-15.
 */
public class CommitsPerHourView {

    private CommitsPerHourData data;
    private Integer maxCommits;
    private final Color MAX = Color.web("#ff0000");
    private final Color ZERO = Color.web("#808080");

    private final Integer HOURS = 24;

    public CommitsPerHourView(CommitsPerHourData data) {
        this.data = data;
        this.maxCommits = data.getMaxCommitsPerDay();
    }

    public void showStage(Stage stage){
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 380, 150, Color.WHITE);
        stage.setResizable(false);
        stage.setWidth(660);
        stage.setHeight(280);
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(5);
        gridpane.setVgap(5);

        for(Days day : Days.values())
            gridpane.add(new Text(day.getShortcut()),0,day.getNumber()+1);
        for(Integer i = 0; i < HOURS; i++)
            gridpane.add(new Text(i.toString()),i+1,0);

        for(Days day : Days.values()){
            for(int i = 0; i < HOURS; i++){
                Rectangle rect = new Rectangle(20,20);
                rect.setFill(chooseColor(data.getDataPerDayAndHour(day,i)));
                gridpane.add(rect,i+1,day.getNumber()+1);
            }
        }
        showDesription(root);
        root.setCenter(gridpane);
        stage.setTitle("Commits per hour of week day");
        stage.setScene(scene);
        stage.show();
    }

    private Color chooseColor(final Integer commits){
        Double percentage = (commits*100.0d) / maxCommits;
        return ZERO.interpolate(MAX,percentage/ 100.0d);
    }

    private void showDesription(BorderPane borderPane){
        Stop[] stops = new Stop[] {new Stop(0,ZERO), new Stop(1,MAX)};
        LinearGradient lg = new LinearGradient(0,0,1,0,true, CycleMethod.NO_CYCLE,stops);
        Rectangle rect = new Rectangle(160,40);
        rect.setFill(lg);
        BorderPane borderPane1 = new BorderPane();
        borderPane1.setCenter(rect);
        borderPane.setBottom(borderPane1);


    }
}

