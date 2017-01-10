package org.repoanalyzer.reporeader.exceptions;

public class CannotOpenAuthorFileException extends Exception {
    public CannotOpenAuthorFileException() {
        super("Cannot open provided file with authors.");
    }
}
