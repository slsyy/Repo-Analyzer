package org.repoanalyzer.statisticsprovider.statistics.commitsperhour;

import org.joda.time.DateTimeConstants;
import org.joda.time.base.AbstractDateTime;

/**
 * Created by Jakub on 2016-12-15.
 */
public enum Days {

    MONDAY("MON",0),
    TUESDAY("TUE",1),
    WEDNESDAY("WED",2),
    THURSDAY("THU",3),
    FRIDAY("FRI",4),
    SATURDAY("SAT",5),
    SUNDAY("SUN",6);


    private final String shortcut;
    private final Integer number;

    Days(String shortcut, Integer number){
        this.shortcut = shortcut;
        this.number = number;
    }

    public String getShortcut() {
        return shortcut;
    }

    public Integer getNumber() {
        return number;
    }

    public static Days fromDateTime(Integer date){
        Days result = null;
        switch (date){
            case DateTimeConstants.MONDAY:
                result=Days.MONDAY;
                break;
            case DateTimeConstants.TUESDAY:
                result=Days.TUESDAY;
                break;
            case DateTimeConstants.WEDNESDAY:
                result=Days.WEDNESDAY;
                break;
            case DateTimeConstants.THURSDAY:
                result=Days.THURSDAY;
                break;
            case DateTimeConstants.FRIDAY:
                result=Days.FRIDAY;
                break;
            case DateTimeConstants.SATURDAY:
                result=Days.SATURDAY;
                break;
            case DateTimeConstants.SUNDAY:
                result=Days.SUNDAY;
                break;
        }
        return result;
    }
}
