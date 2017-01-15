package org.repoanalyzer.reporeader.commit;

import com.google.common.collect.ImmutableSet;
import org.repoanalyzer.reporeader.exceptions.AuthorNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class AuthorProvider {
    protected Set<Author> authors;

    public AuthorProvider(){
        this.authors = new HashSet<>();
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

    public Set<Author> getAuthors() {
        return ImmutableSet.copyOf(this.authors);
    }

    public int getAuthorsNumber() {
        return this.authors.size();
    }

    public boolean doesSetAsStringContainsAuthor(String author) {
        return this.authors.toString().contains(author);
    }

    private Author getByEmailOrName(String name, String email) throws AuthorNotFoundException {
        try {
            return this.findByEmail(email);
        } catch (AuthorNotFoundException e) {
            return this.findByName(name);
        }
    }

    private Author findByEmail(String email) throws AuthorNotFoundException{
        for (Author a : this.authors)
            if (a.containsEmail(email))
                return a;
        throw new AuthorNotFoundException();
    }

    private Author findByName(String name) throws AuthorNotFoundException{
        for (Author a : this.authors)
            if (a.containsEmail(name))
                return a;
        throw new AuthorNotFoundException();
    }

    private  Author createNewAuthor(String name, String email) {
        Author author = new Author(name, email);
        this.authors.add(author);
        return author;
    }
}
