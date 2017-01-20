package org.repoanalyzer.reporeader.author.authorfileparser;

import org.repoanalyzer.reporeader.author.Author;
import org.repoanalyzer.reporeader.exceptions.InvalidJsonDataFormatException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonAuthorParser {
    public JsonAuthorParser() {}

    public Author parse(JSONObject jsonAuthor) throws InvalidJsonDataFormatException {
        if (jsonAuthor.keySet().size() != 1) throw new InvalidJsonDataFormatException();

        String authorName = (String) jsonAuthor.keySet().toArray()[0];
        Object objectAuthorMails = jsonAuthor.get(authorName);

        if (!(objectAuthorMails instanceof JSONArray)) throw new InvalidJsonDataFormatException();

        AuthorFromJsonBuilder authorBuilder = new AuthorFromJsonBuilder(authorName);
        return authorBuilder.addJsonArrayOfEmails((JSONArray) objectAuthorMails).build();
    }
}
