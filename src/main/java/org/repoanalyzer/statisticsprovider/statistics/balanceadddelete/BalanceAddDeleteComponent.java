package org.repoanalyzer.statisticsprovider.statistics.balanceadddelete;

import javafx.stage.Stage;
import org.repoanalyzer.reporeader.commit.Author;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.statistics.AbstractStatisticsComponent;

import java.util.List;
import java.util.Set;

/**
 * Created by Jakub on 2016-12-18.
 */
public class BalanceAddDeleteComponent extends AbstractStatisticsComponent {

    private BalanceAddDeleteCalculator calculator;
    private BalanceAddDeleteView view;

    public BalanceAddDeleteComponent(Set<Author> authors) {
        super(authors);
        calculator = new BalanceAddDeleteCalculator(authors);
        view = new BalanceAddDeleteView(calculator.generateData());
    }

    @Override
    public void createAndShowStatisticsView() {
        this.view.showStage(new Stage());
    }

    public BalanceAddDeleteView getView() {
        return view;
    }
}
