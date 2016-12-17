package org.repoanalyzer.statisticsprovider.component;


import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.calculator.HeatMapCalculator;
import org.repoanalyzer.statisticsprovider.calculator.RevertStatisticsCalculator;
import org.repoanalyzer.statisticsprovider.view.HeatMapView;
import org.repoanalyzer.statisticsprovider.view.RevertStatisticsView;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class HeatMapComponent extends AbstractStatisticsComponent{
    private HeatMapView view;
    private HeatMapCalculator calculator;

    public HeatMapComponent(Future<List<Commit>> commits) {
        super(commits);
        calculator = new HeatMapCalculator(commits);
        try {
            view = new HeatMapView(calculator.generateData(), commits.get().size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void createStatisticsView() {
        // todo
    }

    public HeatMapView getView(){
        return view;
    }
}
