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

    public void setState(String state) {
        this.state = state;
    }
}
