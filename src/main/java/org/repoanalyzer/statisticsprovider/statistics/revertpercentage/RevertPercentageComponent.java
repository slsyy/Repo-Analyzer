package org.repoanalyzer.statisticsprovider.statistics.revertpercentage;


import javafx.stage.Stage;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.statistics.AbstractStatisticsComponent;

import java.util.List;

public class RevertPercentageComponent extends AbstractStatisticsComponent {

    private RevertPercentageView revertView;
    private RevertPercentageCalculator revertCalculator;

    public RevertPercentageComponent(List<Commit> commits) {
        super(commits);
        revertCalculator= new RevertPercentageCalculator(commits);
        revertView = new RevertPercentageView(revertCalculator.generateStatistics());
    }

    public void createAndShowStatisticsView() {
        revertView.createView(new Stage());
    }
}
