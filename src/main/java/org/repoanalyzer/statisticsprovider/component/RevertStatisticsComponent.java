package org.repoanalyzer.statisticsprovider.component;


import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.calculator.RevertStatisticsCalculator;
import org.repoanalyzer.statisticsprovider.view.RevertStatisticsView;

import java.util.List;
import java.util.concurrent.Future;

public class RevertStatisticsComponent extends AbstractStatisticsComponent {

    private RevertStatisticsView revertView;
    private RevertStatisticsCalculator revertCalculator;

    public RevertStatisticsComponent(Future<List<Commit>> commits) {
        super(commits);
    }

    public void createStatisticsView() {

    }
}
