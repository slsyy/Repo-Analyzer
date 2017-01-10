package org.repoanalyzer.reporeader.exceptions;

public class JsonParsingException extends Exception {
    public JsonParsingException(int lane) {
        super("Error occurred while parsing provided file with authors - expecting JSON, lane: " + Integer.toString(lane));
    }
}
