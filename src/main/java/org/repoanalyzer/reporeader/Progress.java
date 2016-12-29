package org.repoanalyzer.reporeader;

public class Progress {
    private long workDone;
    private long max;

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
}
