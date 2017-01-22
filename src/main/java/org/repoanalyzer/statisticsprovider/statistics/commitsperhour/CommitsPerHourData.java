package org.repoanalyzer.statisticsprovider.statistics.commitsperhour;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jakub on 2016-12-15.
 */
public class CommitsPerHourData {
    private Table<Days, Integer, Integer> data = HashBasedTable.create();


    private int maxCommitsPerDay = 0;

    public CommitsPerHourData() {
    }

    public void incrementCommits(Days day, Integer hour){
        Integer oldValue = data.get(day,hour);
        data.remove(day,hour);
        data.put(day,hour,oldValue+1);
    }

    public int getMaxCommitsPerDay() {
        return maxCommitsPerDay;
    }

    public void trySetMaxCommitsPerDay(int maxCommitsPerDay) {
        if(maxCommitsPerDay>this.maxCommitsPerDay)
            this.maxCommitsPerDay = maxCommitsPerDay;
    }

    public void putEmptyData(Days day, int hour){
        data.put(day,hour,0);
    }

    public int getDataPerDayAndHour(Days day, int hour){
        return data.get(day,hour);
    }

    public Set<Integer> getAllValues(){
        return new HashSet<>(data.values());
    }
}
