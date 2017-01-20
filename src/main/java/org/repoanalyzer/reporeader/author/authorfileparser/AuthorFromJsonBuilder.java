package org.repoanalyzer.reporeader.author.authorfileparser;

import org.json.simple.JSONArray;
import org.repoanalyzer.reporeader.author.Author;
import org.repoanalyzer.reporeader.exceptions.InvalidJsonDataFormatException;

public class AuthorFromJsonBuilder {
    private final String name;
    private JSONArray emails;

    public AuthorFromJsonBuilder(String name) {
        this.name = name;
    }

    public AuthorFromJsonBuilder addJsonArrayOfEmails(JSONArray emails) {
        this.emails = emails;

        return this;
    }

    public Author build() throws InvalidJsonDataFormatException {
        Author author = new Author(name);
        for (Object authorMail : emails) {
            if (!(authorMail instanceof String)) throw new InvalidJsonDataFormatException();
            author.addEmailIfNotExists((String) authorMail);
        }

        return author;
    }
}
