package org.repoanalyzer.statisticsprovider;


import org.repoanalyzer.statisticsprovider.controllers.StatisticsController;

public class App {
    public static void main(String args[]){
        StatisticsController controller = new StatisticsController();
        controller.startApplication();
    }
}
