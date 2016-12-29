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



    private Integer numOfCommits = 0;
    private Integer maxCommitsPerDay = 0;

    public CommitsPerHourData() {
    }

    public Integer getNumOfCommits() {
        return numOfCommits;
    }

    public void incrementCommits(Days day, Integer hour){
        Integer oldValue = data.get(day,hour);
        data.remove(day,hour);
        data.put(day,hour,oldValue+1);
    }

    public Integer getMaxCommitsPerDay() {
        return maxCommitsPerDay;
    }

    public void trySetMaxCommitsPerDay(Integer maxCommitsPerDay) {
        if(maxCommitsPerDay>this.maxCommitsPerDay)
            this.maxCommitsPerDay = maxCommitsPerDay;
    }

    public void putEmptyData(Days day, Integer hour){
        data.put(day,hour,0);
    }

    public Integer getDataPerDayAndHour(Days day, Integer hour){
        return data.get(day,hour);
    }

    public Set<Integer> getAllValues(){
        return new HashSet<>(data.values());
    }
}
