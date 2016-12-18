package org.repoanalyzer.reporeader;

public class Progress {
    private long workDone;
    private long max;
//    private String state;


//    public String getState() { return state; }

    public long getWorkDone() { return workDone; }

    public void setWorkDone(long workDone) {
        this.workDone = workDone;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

//    public void setState(char state) {
//        if (state == 'P')
//            this.state = "Pending for request.";
//        else if (state == 'R')
//            this.state = "Reading from repository...";
//        else if (state == 'T')
//            this.state = "Preparing commits...";
//        else
//            this.state = "Unknown state.";
//    }
}
