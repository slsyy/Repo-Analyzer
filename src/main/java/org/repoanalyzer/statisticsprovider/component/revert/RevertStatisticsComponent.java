package org.repoanalyzer.statisticsprovider.component.revert;


import javafx.stage.Stage;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.component.AbstractStatisticsComponent;

import java.util.List;

public class RevertStatisticsComponent extends AbstractStatisticsComponent {

    private RevertStatisticsView revertView;
    private RevertStatisticsCalculator revertCalculator;

    public RevertStatisticsComponent(List<Commit> commits) {
        super(commits);
        revertCalculator= new RevertStatisticsCalculator(commits);
        revertView = new RevertStatisticsView(revertCalculator.generateStatistics());
    }

    public void createAndShowStatisticsView() {
        revertView.createView(new Stage());
    }
}
