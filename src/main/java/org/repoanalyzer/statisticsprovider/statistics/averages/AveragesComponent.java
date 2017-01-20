package org.repoanalyzer.statisticsprovider.statistics.averages;

import javafx.stage.Stage;
import org.repoanalyzer.reporeader.author.Author;
import org.repoanalyzer.statisticsprovider.statistics.AbstractStatisticsComponent;

import java.util.Set;

/**
 * Created by Jakub on 2017-01-14.
 */
public class AveragesComponent extends AbstractStatisticsComponent{

    private AveragesCalculator averagesCalculator;
    private AveragesView averagesView;

    public AveragesComponent(Set<Author> authors) {
        super(authors);
        averagesCalculator = new AveragesCalculator(authors);
        averagesView = new AveragesView(averagesCalculator.generateData());
    }

    @Override
    public void createAndShowStatisticsView() {
        averagesView.showStage(new Stage());
    }
}
