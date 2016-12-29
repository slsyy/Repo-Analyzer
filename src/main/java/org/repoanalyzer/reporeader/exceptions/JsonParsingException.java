package org.repoanalyzer.reporeader.exceptions;

public class JsonParsingException extends Exception {
    private int lane;

    public JsonParsingException(int lane) {
        super();
        this.lane = lane;
    }

    public int getLaneNumber() { return this.lane; }
}
