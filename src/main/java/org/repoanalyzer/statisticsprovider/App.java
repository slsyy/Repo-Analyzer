package org.repoanalyzer.statisticsprovider;

import javafx.stage.Stage;
import org.repoanalyzer.reporeader.IRepoReader;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.view.MainStage;
import org.repoanalyzer.statisticsprovider.view.RepoPathReaderView;

import  java.lang.System;

public class App {
    public static void main(String args[]){

        StatisticsController controller = new StatisticsController();
        controller.startApplication();
    }
}
