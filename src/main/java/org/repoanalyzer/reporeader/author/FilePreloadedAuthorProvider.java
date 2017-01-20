package org.repoanalyzer.reporeader.author;

import org.repoanalyzer.reporeader.author.authorfileparser.AuthorFileParser;
import org.repoanalyzer.reporeader.exceptions.CannotOpenAuthorFileException;
import org.repoanalyzer.reporeader.exceptions.InvalidJsonDataFormatException;
import org.repoanalyzer.reporeader.exceptions.JsonParsingException;

import java.io.FileReader;
import java.io.IOException;

public class FilePreloadedAuthorProvider extends AuthorProvider {
    public FilePreloadedAuthorProvider(String authorFile) throws JsonParsingException, CannotOpenAuthorFileException, InvalidJsonDataFormatException {
        super();
        addAuthorsFromFile(authorFile);
    }

    private void addAuthorsFromFile(String authorFile) throws CannotOpenAuthorFileException,
            InvalidJsonDataFormatException, JsonParsingException {
        AuthorFileParser fileParser = new AuthorFileParser();
        try {
            this.authors = fileParser.parse(new FileReader(authorFile));
        } catch (IOException e) {
            throw new CannotOpenAuthorFileException();
        }
    }
}
