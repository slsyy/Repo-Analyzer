package org.repoanalyzer.reporeader;

public class Progress {
    private float progressFraction;
    private String state;


    public float getProgressFraction() {
        return progressFraction;
    }

    public void setProgressFraction(float progressFraction) {
        this.progressFraction = progressFraction;
    }

    public String getState() {
        return state;
    }

    public void setState(char state) {
        if (state == 'P')
            this.state = "Pending for request.";
        else if (state == 'R')
            this.state = "Reading from repository...";
        else if (state == 'T')
            this.state = "Preparing commits...";
        else
            this.state = "Unknown state.";
    }
}
