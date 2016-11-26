package org.repoanalyzer.statisticsprovider.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import org.repoanalyzer.reporeader.IRepoReader;
import org.repoanalyzer.reporeader.commit.Author;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.MockedRepoReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakub on 2016-11-26.
 */
public class CommitStatisticStage {

    //TODO Move to model
    //TODO Refactor me pls
    final IRepoReader repoReader;
    public CommitStatisticStage() {
        this.repoReader = new MockedRepoReader("uerel");
    }

    private List<PieChart.Data> calculateStatistic() {
        List<PieChart.Data> data = new ArrayList<PieChart.Data>();

        List<Commit> commits = repoReader.getCommits();
        List<Author> authors = new ArrayList<Author>();

        for(Commit commit: commits){
            if(!authors.contains(commit.getAuthor())){
                authors.add(commit.getAuthor());
            }
        }

        for(Author author: authors){
            int authorCommitsNumber = 0;
            for(Commit commit: commits){
                if(author.equals(commit.getAuthor())) {
                    authorCommitsNumber++;
                }
            }
            data.add(new PieChart.Data(author.getName(), authorCommitsNumber));
        }
        return data;
    }

    public void showStage(Stage stage) throws Exception {
        Scene scene = new Scene(new Group());
        stage.setTitle("Commits statistic");
        stage.setWidth(500);
        stage.setHeight(500);


        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(calculateStatistic());
        final PieChart chart = new PieChart(pieChartData);
        chart.setMaxWidth(500);
        chart.setMaxHeight(500);
        chart.setTitle("Commits");

        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();
    }

}
