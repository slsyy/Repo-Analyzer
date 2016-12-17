package org.repoanalyzer.statisticsprovider.component;


import javafx.stage.Stage;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.calculator.CommitPercentageCalculator;
import org.repoanalyzer.statisticsprovider.view.CommitPercentageView;

import java.util.List;

public class CommitPercentageComponent extends AbstractStatisticsComponent{
    private CommitPercentageView view;
    private CommitPercentageCalculator calculator;

    public CommitPercentageComponent(List<Commit> commits) {
        super(commits);
        calculator = new CommitPercentageCalculator(commits);
        view = new CommitPercentageView(calculator.generateData());
    }

    public void createStatisticsView() {
        this.view.showStage(new Stage());
    }

    public CommitPercentageView getView(){
        return view;
    }
}
