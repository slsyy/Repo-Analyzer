package org.repoanalyzer.statisticsprovider.calculator;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.data.Days;
import org.repoanalyzer.statisticsprovider.data.HeatMapData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by Jakub on 2016-12-15.
 */
public class HeatMapCalculator {
    private List<Commit> commits;

    public HeatMapCalculator(Future<List<Commit>> commits) {
        try {
            this.commits = commits.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public List<HeatMapData> generateData(){
        List<HeatMapData> result = new ArrayList<HeatMapData>();
        for(Days day : Days.values())
            for(int i =0; i< 24; i++)
                result.add(new HeatMapData(day,i));

        Days day = null;

        for(Commit commit : commits){
            Integer hour  = commit.getDateTime().getHourOfDay();
            switch (commit.getDateTime().getDayOfWeek()){
                case DateTimeConstants.MONDAY:
                    day=Days.MONDAY;
                    break;
                case DateTimeConstants.TUESDAY:
                    day=Days.TUESDAY;
                    break;
                case DateTimeConstants.WEDNESDAY:
                    day=Days.WEDNESDAY;
                    break;
                case DateTimeConstants.THURSDAY:
                    day=Days.THURSDAY;
                    break;
                case DateTimeConstants.FRIDAY:
                    day=Days.FRIDAY;
                    break;
                case DateTimeConstants.SATURDAY:
                    day=Days.SATURDAY;
                    break;
                case DateTimeConstants.SUNDAY:
                    day=Days.SUNDAY;
                    break;
            }
            for(HeatMapData hmd: result)
                if(hmd.getDay().equals(day) && hmd.getHour().equals(hour))
                    hmd.incrementCommits();
        }
        return result;
    }

}
