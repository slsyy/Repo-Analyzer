package org.repoanalyzer.reporeader.exceptions;

public class RepositoryNotFoundOrInvalidException extends Exception {
    public RepositoryNotFoundOrInvalidException() {
        super("Broken Repository");
    }
    public RepositoryNotFoundOrInvalidException(String message) {
        super(message);
    }
}
