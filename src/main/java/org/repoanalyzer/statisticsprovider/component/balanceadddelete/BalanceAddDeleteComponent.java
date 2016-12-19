package org.repoanalyzer.statisticsprovider.component.balanceadddelete;

import javafx.stage.Stage;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.component.AbstractStatisticsComponent;

import java.util.List;

/**
 * Created by Jakub on 2016-12-18.
 */
public class BalanceAddDeleteComponent extends AbstractStatisticsComponent {

    private BalanceAddDeleteCalculator calculator;
    private BalanceAddDeleteView view;

    public BalanceAddDeleteComponent(List<Commit> commits) {
        super(commits);
        calculator = new BalanceAddDeleteCalculator(commits);
        view = new BalanceAddDeleteView(calculator.generateData());
    }

    @Override
    public void createStatisticsView() {
        this.view.showStage(new Stage());
    }

    public BalanceAddDeleteView getView() {
        return view;
    }
}
