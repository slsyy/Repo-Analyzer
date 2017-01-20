package org.repoanalyzer.statisticsprovider.statistics.commitpercentage;


import javafx.stage.Stage;
import org.repoanalyzer.reporeader.author.Author;
import org.repoanalyzer.statisticsprovider.statistics.AbstractStatisticsComponent;

import java.util.Set;

public class CommitPercentageComponent extends AbstractStatisticsComponent {
    private CommitPercentageView view;
    private CommitPercentageCalculator calculator;

    public CommitPercentageComponent(Set<Author> authors) {
        super(authors);
        calculator = new CommitPercentageCalculator(authors);
        view = new CommitPercentageView(calculator.generateData());
    }

    public void createAndShowStatisticsView() {
        this.view.showStage(new Stage());
    }

    public CommitPercentageView getView(){
        return view;
    }
}
