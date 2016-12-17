package org.repoanalyzer.reporeader.commit;

import java.util.LinkedList;

public class Author {

    private LinkedList<String> names;
    private LinkedList<String> emails;

    public Author(String name, String email) {
        this.names.add(name);
        this.emails.add(email);
    }

    public LinkedList<String> getNames() {
        return names;
    }

    public String getFirstName() {
        return names.getFirst();
    }

    public LinkedList<String> getEmails() {
        return emails;
    }

    public void addName(String name) {
        names.add(name);
    }

    public void addEmail(String email) {
        emails.add(email);
    }
}
