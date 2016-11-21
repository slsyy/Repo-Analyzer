package org.repoanalyzer.reporeader.commit;


final public class Author {
    public Author(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    final private String name;
    final private String email;
}
