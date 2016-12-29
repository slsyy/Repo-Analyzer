package org.repoanalyzer.statisticsprovider.statistics.commitsperhour;

import org.repoanalyzer.reporeader.commit.Commit;

import java.util.List;


public class CommitsPerHourCalculator {
    private List<Commit> commits;

    public CommitsPerHourCalculator(List<Commit> commits) {
        this.commits = commits;
    }

    public CommitsPerHourData generateData(){
        CommitsPerHourData result = new CommitsPerHourData();
        for(Days day : Days.values())
            for(int i =0; i< 24; i++)
                result.putEmptyData(day,i);
        Days day = null;
        for(Commit commit : commits){
            Integer hour  = commit.getDateTime().getHourOfDay();
            day=Days.fromDateTime(commit.getDateTime().getDayOfWeek());
            result.incrementCommits(day,hour);
        }

        for(Integer value : result.getAllValues())
            result.trySetMaxCommitsPerDay(value);
        return result;
    }

}
