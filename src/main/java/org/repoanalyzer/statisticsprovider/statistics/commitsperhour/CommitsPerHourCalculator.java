package org.repoanalyzer.statisticsprovider.statistics.commitsperhour;

import org.joda.time.DateTimeConstants;
import org.repoanalyzer.reporeader.commit.Commit;

import java.util.ArrayList;
import java.util.List;


public class CommitsPerHourCalculator {
    private List<Commit> commits;

    public CommitsPerHourCalculator(List<Commit> commits) {
        this.commits = commits;
    }

    public List<CommitsPerHourData> generateData(){
        List<CommitsPerHourData> result = new ArrayList<CommitsPerHourData>();
        for(Days day : Days.values())
            for(int i =0; i< 24; i++)
                result.add(new CommitsPerHourData(day,i));

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
            for(CommitsPerHourData hmd: result)
                if(hmd.getDay().equals(day) && hmd.getHour().equals(hour))
                    hmd.incrementCommits();
        }
        return result;
    }

}
