package org.repoanalyzer.statisticsprovider.statistics.commitsperhour;


import javafx.stage.Stage;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.statistics.AbstractStatisticsComponent;

import java.util.List;
public class CommitsPerHourComponent extends AbstractStatisticsComponent {
    private CommitsPerHourView view;
    private CommitsPerHourCalculator calculator;

    public CommitsPerHourComponent(List<Commit> commits) {
        super(commits);
        calculator = new CommitsPerHourCalculator(commits);
        view = new CommitsPerHourView(calculator.generateData(), commits.size());
    }

    public void createAndShowStatisticsView() {
        this.view.showStage(new Stage());
    }

    public CommitsPerHourView getView(){
        return view;
    }
}
