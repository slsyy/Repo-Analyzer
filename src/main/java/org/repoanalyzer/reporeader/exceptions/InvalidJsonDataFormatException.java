package org.repoanalyzer.reporeader.exceptions;

public class InvalidJsonDataFormatException extends Exception{
    public InvalidJsonDataFormatException() {
        super("Invalid format of data in provided file with authors.");
    }
}
