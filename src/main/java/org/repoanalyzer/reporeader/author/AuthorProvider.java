package org.repoanalyzer.reporeader.author;

import org.repoanalyzer.reporeader.exceptions.AuthorNotFoundException;

import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Set;

public class AuthorProvider {
    protected HashSet<Author> authors;

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

    public boolean doesAuthorExistInList(Author author){
        for(Author i: this.authors)
            if(author.equals(i))
                return true;
        return false;
    }

    private Author getByEmailOrName(String name, String email) throws AuthorNotFoundException {
        Author author = this.findByEmail(email);
        if (author == null) {
            author = this.findByName(name);
            if (author == null) throw new AuthorNotFoundException();
        }
        return author;
    }

    private Author findByEmail(String email) {
        for (Author a : this.authors)
            if (a.containsEmail(email))
                return a;
        return null;
    }

    private Author findByName(String name) {
        for (Author a : this.authors)
            if (a.containsName(name))
                return a;
        return null;
    }

    private  Author createNewAuthor(String name, String email) {
        Author author = new Author(name, email);
        this.authors.add(author);
        return author;
    }
}
