package org.repoanalyzer.reporeader;

public class Progress {
    private final long workDone;
    private final long max;

    public Progress (long workDone, long max) {
        this.workDone = workDone;
        this.max = max;
    }

    public long getWorkDone() {
        return workDone;
    }

    public long getMax() {
        return max;
    }

}
