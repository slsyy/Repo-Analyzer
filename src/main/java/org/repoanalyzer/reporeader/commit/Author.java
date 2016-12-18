package org.repoanalyzer.reporeader.commit;

import java.util.LinkedList;
import java.util.List;

public class Author {

    private LinkedList<String> names;
    private LinkedList<String> emails;
    private LinkedList<Commit> commits;

    public Author(String name, String email) {
        this.names = new LinkedList<>();
        this.emails = new LinkedList<>();
        this.commits = new LinkedList<>();

        this.names.add(name);
        this.emails.add(email);
    }

    public List<String> getNames() {
        return names;
    }

    public String getFirstName() {
        return names.getFirst();
    }

    public List<String> getEmails() {
        return emails;
    }

    public void addName(String name) {
        names.add(name);
    }

    public void addEmail(String email) {
        emails.add(email);
    }

    public void addCommit(Commit commit) {
        commits.add(commit);
    }

    public LinkedList<Commit> getCommits() {
        return commits;
    }

    @Override
    public String toString(){
        return this.getFirstName();
    }
}
