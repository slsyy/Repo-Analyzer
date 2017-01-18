package org.repoanalyzer.reporeader.commit;

import com.google.common.collect.ImmutableList;

import java.util.LinkedList;
import java.util.List;

public class Author {
    private List<String> names;
    private List<String> emails;
    private List<Commit> commits;

    public Author(String name) {
        this.names = new LinkedList<>();
        this.emails = new LinkedList<>();
        this.commits = new LinkedList<>();

        this.names.add(name);
    }

    public Author(String name, String email) {
        this(name);
        this.emails.add(email);
    }

    public int getNamesNumber() {
        return this.names.size();
    }

    public boolean containsName(String name) {
        return this.names.contains(name);
    }

    public String getFirstName() {
        return this.names.get(0);
    }

    public int getEmailsNumber() {
        return this.emails.size();
    }

    public boolean containsEmail(String email) {
        return this.emails.contains(email);
    }

    public void addNameIfNotExists(String name) {
        if (!this.names.contains(name))
            this.names.add(name);
    }

    public void addEmailIfNotExists(String email) {
        if (!this.emails.contains(email))
            this.emails.add(email);
    }

    public void addCommit(Commit commit) {
        this.commits.add(commit);
    }

    public void addCommits(List<Commit> commits){
        this.commits.addAll(commits);
    }

    public List<Commit> getCommits() {
        return ImmutableList.copyOf(this.commits);
    }

    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof Author) {
            Author that = (Author) other;
            result = (this.names.equals(that.names) && this.emails.equals(that.emails));
        }
        return result;
    }

    @Override
    public String toString(){
        return this.names.toString() + ":" + this.emails.toString();
    }
}
