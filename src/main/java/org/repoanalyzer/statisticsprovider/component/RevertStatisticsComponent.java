package org.repoanalyzer.statisticsprovider.component;


import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.calculator.RevertStatisticsCalculator;
import org.repoanalyzer.statisticsprovider.view.RevertStatisticsView;

import java.util.List;

public class RevertStatisticsComponent extends AbstractStatisticsComponent {

    private RevertStatisticsView revertView;
    private RevertStatisticsCalculator revertCalculator;

    public RevertStatisticsComponent(List<Commit> commits) {
        super(commits);
    }

    public void createStatisticsView() {

    }
}
