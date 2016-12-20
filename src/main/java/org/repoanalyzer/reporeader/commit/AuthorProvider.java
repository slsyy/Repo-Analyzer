package org.repoanalyzer.reporeader.commit;

import org.repoanalyzer.reporeader.exceptions.CannotOpenAuthorFileException;
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

    public void addAuthorsFromFile (String authorFile) throws CannotOpenAuthorFileException{
        //TODO I'll implement here parsing from JSON file
    }
}
