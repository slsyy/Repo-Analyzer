package org.repoanalyzer.reporeader.exceptions;

public class AuthorNotFoundException extends Exception {
    public AuthorNotFoundException() {
        super("None of already existing authors contain given name nor mail");
    }
}
