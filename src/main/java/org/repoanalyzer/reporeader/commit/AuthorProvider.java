package org.repoanalyzer.reporeader.commit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.repoanalyzer.reporeader.exceptions.CannotOpenAuthorFileException;
import org.repoanalyzer.reporeader.exceptions.InvalidJsonDataFormatException;
import org.repoanalyzer.reporeader.exceptions.JsonParsingException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class AuthorProvider {
    private HashSet<Author> authors;

    public AuthorProvider(){
        this.authors = new HashSet<Author>();
    }

    public Author getCreateOrUpdateAuthor(String name, String email) {
        Author author = null;
        Boolean foundName = false;
        Boolean foundMail = false;

        for (Author a : authors)
            if (a.getNames().contains(name)) {
                author = a;
                foundName = true;
                break;
            }

        if (foundName && author.getEmails().contains(email))
            foundMail = true;
        else if (!foundName) {
            for (Author a : authors)
                if (a.getEmails().contains(email)) {
                    author = a;
                    foundMail = true;
                    break;
                }
        }

        if (!foundName && !foundMail) {
            author = new Author(name, email);
            authors.add(author);
        }
        else if (!foundName)
            author.addName(name);
        else if (!foundMail)
            author.addEmail(email);

        return author;
    }

    public void addAuthorsFromFile (String authorFile) throws CannotOpenAuthorFileException,
            InvalidJsonDataFormatException, JsonParsingException {
        try{
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
                    author.addEmail((String) authorMail);
                }
                authors.add(author);
            }
        }catch(ParseException pe){
            throw new JsonParsingException(pe.getPosition());
        } catch (FileNotFoundException e) {
            throw new CannotOpenAuthorFileException();
        } catch (IOException e) {
            throw new CannotOpenAuthorFileException();
        }
    }
}
