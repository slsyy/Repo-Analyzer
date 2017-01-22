package org.repoanalyzer.statisticsprovider.controllers;


import javafx.application.Application;
import javafx.stage.Stage;
import org.repoanalyzer.statisticsprovider.view.RepoPathReaderView;

public class StatisticsController extends Application {

    public void startApplication(){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        RepoReaderTaskController repoReaderTaskController = new RepoReaderTaskController(new StatisticComponentsCreator());
        new RepoPathReaderView(repoReaderTaskController).showStage(new Stage());
    }
}
