package org.repoanalyzer.reporeader.commit;

import java.util.HashSet;
import java.util.Set;

public class AuthorProvider {
    private Set<Author> authors;

    public AuthorProvider(){
        this.authors = new HashSet<Author>();
    }

    public Author getCreateOrUpdateAuthor(String name, String email){
        return null;
    }

    private Author addAuthor(String name, String email){
        return null;
    }

}
