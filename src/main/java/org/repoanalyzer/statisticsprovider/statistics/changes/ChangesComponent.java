package org.repoanalyzer.statisticsprovider.statistics.changes;

import javafx.stage.Stage;
import org.repoanalyzer.reporeader.commit.Author;
import org.repoanalyzer.statisticsprovider.statistics.AbstractStatisticsComponent;

import java.util.Set;

/**
 * Created by Jakub on 2017-01-14.
 */
public class ChangesComponent extends AbstractStatisticsComponent {
    private ChangesCalculator changesCalculator;
    private ChangesView changesView;

    public ChangesComponent(Set<Author> authors) {
        super(authors);
        changesCalculator = new ChangesCalculator(authors);
        changesView = new ChangesView(changesCalculator.generateData());
    }

    @Override
    public void createAndShowStatisticsView() {
        changesView.showStage(new Stage());
    }
}
