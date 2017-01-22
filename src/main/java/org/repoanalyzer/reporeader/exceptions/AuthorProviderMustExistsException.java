package org.repoanalyzer.reporeader.exceptions;

public class AuthorProviderMustExistsException extends Exception {
    public AuthorProviderMustExistsException() {
        super("AuthorProvider cannot be null");
    }
}
