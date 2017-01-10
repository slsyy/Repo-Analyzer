package org.repoanalyzer.reporeader.commit;

import org.repoanalyzer.reporeader.exceptions.AuthorNotFoundException;

import java.util.HashSet;

public class AuthorProvider {
    protected HashSet<Author> authors;

    public AuthorProvider(){
        this.authors = new HashSet<Author>();
    }

    public Author getCreateOrUpdateAuthor(String name, String email) {
        Author author;

        try {
            author = getByEmailOrName(name, email);

            author.addNameIfNotExists(name);
            author.addEmailIfNotExists(email);
        } catch (AuthorNotFoundException e) {
            author = createNewAuthor(name, email);
        }

        return author;
    }

    public int getAuthorsSize() {
        return authors.size();
    }

    public boolean doesSetAsStringContainsAuthor(String author) {
        return authors.toString().contains(author);
    }

    private Author getByEmailOrName(String name, String email) throws AuthorNotFoundException {
        try {
            return this.getByEmail(email);
        } catch (AuthorNotFoundException e) {
            return this.findByName(name);
        }
    }

    private Author getByEmail(String email) throws AuthorNotFoundException{
        for (Author a : authors)
            if (a.getEmails().contains(email))
                return a;
        throw new AuthorNotFoundException();
    }

    private Author findByName(String name) throws AuthorNotFoundException{
        for (Author a : authors)
            if (a.getNames().contains(name))
                return a;
        throw new AuthorNotFoundException();
    }

    private  Author createNewAuthor(String name, String email) {
        Author author = new Author(name, email);
        authors.add(author);
        return author;
    }
}
