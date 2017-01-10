package org.repoanalyzer.reporeader.commit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

    private void addAuthorsFromFile (String authorFile) throws CannotOpenAuthorFileException,
            InvalidJsonDataFormatException, JsonParsingException {
        try {
            JSONParser parser = new JSONParser();
            Object objectArrayOfAuthors = parser.parse(new FileReader(authorFile));

            if (!(objectArrayOfAuthors instanceof JSONArray)) throw new InvalidJsonDataFormatException();
            JSONArray jsonArrayOfAuthors = (JSONArray) objectArrayOfAuthors;

            for (Object objectAuthor : jsonArrayOfAuthors) {
                if (!(objectAuthor instanceof JSONObject)) throw new InvalidJsonDataFormatException();
                JSONObject jsonAuthor = (JSONObject) objectAuthor;

                if (jsonAuthor.keySet().size() != 1) throw new InvalidJsonDataFormatException();
                String authorName = (String) jsonAuthor.keySet().toArray()[0];
                Object objectAuthorMails = jsonAuthor.get(authorName);
                if (!(objectAuthorMails instanceof JSONArray)) throw new InvalidJsonDataFormatException();
                JSONArray jsonAuthorMails = (JSONArray) objectAuthorMails;

                Author author = new Author(authorName);
                for (Object authorMail : jsonAuthorMails) {
                    if (!(authorMail instanceof String)) throw new InvalidJsonDataFormatException();
                    author.addEmailIfNotExists((String) authorMail);
                }
                this.authors.add(author);
            }
        } catch (ParseException pe) {
            throw new JsonParsingException(pe.getPosition());
        } catch (IOException e) {
            throw new CannotOpenAuthorFileException();
        }
    }
}
