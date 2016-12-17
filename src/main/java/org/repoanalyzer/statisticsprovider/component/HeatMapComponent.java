package org.repoanalyzer.statisticsprovider.component;


import javafx.stage.Stage;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.calculator.HeatMapCalculator;
import org.repoanalyzer.statisticsprovider.view.HeatMapView;

import java.util.List;
public class HeatMapComponent extends AbstractStatisticsComponent{
    private HeatMapView view;
    private HeatMapCalculator calculator;

    public HeatMapComponent(List<Commit> commits) {
        super(commits);
        calculator = new HeatMapCalculator(commits);
        view = new HeatMapView(calculator.generateData(), commits.size());
    }

    public void createStatisticsView() {
        this.view.showStage(new Stage());
    }

    public HeatMapView getView(){
        return view;
    }
}
