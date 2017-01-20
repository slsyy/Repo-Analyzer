package org.repoanalyzer.reporeader.author.authorfileparser;

import org.repoanalyzer.reporeader.author.Author;
import org.repoanalyzer.reporeader.exceptions.CannotOpenAuthorFileException;
import org.repoanalyzer.reporeader.exceptions.InvalidJsonDataFormatException;
import org.repoanalyzer.reporeader.exceptions.JsonParsingException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;

public class AuthorFileParser {
    public AuthorFileParser() {}

    public HashSet<Author> parse(Reader in) throws InvalidJsonDataFormatException,
            JsonParsingException, CannotOpenAuthorFileException {
        HashSet<Author> authors = new HashSet<>();
        JsonAuthorParser authorParser = new JsonAuthorParser();
        JSONParser parser = new JSONParser();

        try {
            Object objectArrayOfAuthors = parser.parse(in);
            if (!(objectArrayOfAuthors instanceof JSONArray)) throw new InvalidJsonDataFormatException();

            JSONArray jsonArrayOfAuthors = (JSONArray)objectArrayOfAuthors;
            for (Object objectAuthor : jsonArrayOfAuthors) {
                if (!(objectAuthor instanceof JSONObject)) throw new InvalidJsonDataFormatException();
                authors.add(authorParser.parse((JSONObject) objectAuthor));
            }
        } catch (ParseException pe) {
            throw new JsonParsingException(pe.getPosition());
        } catch (IOException e) {
            throw new CannotOpenAuthorFileException();
        }

        return authors;
    }
}
