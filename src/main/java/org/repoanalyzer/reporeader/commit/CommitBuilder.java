package org.repoanalyzer.reporeader.commit;

import org.joda.time.DateTime;

public class CommitBuilder {
    private String hashCode;
    private DateTime dateTime;
    private String message;
    private int deletedLinesNumber;
    private int addedLinesNumber;
    private int changedLinesNumber;

    private String authorName;
    private String authorEmail;
    private Author author;
    private AuthorProvider authorProvider;

    public CommitBuilder(){
    }

    public CommitBuilder(AuthorProvider authorProvider){
        this.authorProvider = authorProvider;
    }

    public CommitBuilder setAuthorName(String authorName){
        this.authorName = authorName;
        return this;
    }

    public CommitBuilder setAuthorEmail(String authorEmail){
        this.authorEmail = authorEmail;
        return this;
    }

    public CommitBuilder setHashCode(String hashCode) {
        this.hashCode = hashCode;
        return this;
    }

    public CommitBuilder setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public CommitBuilder setDate(DateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public CommitBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public CommitBuilder setDeletedLinesNumber(int deletedLinesNumber) {
        this.deletedLinesNumber = deletedLinesNumber;
        return this;
    }

    public CommitBuilder setAddedLinesNumber(int addedLinesNumber) {
        this.addedLinesNumber = addedLinesNumber;
        return this;
    }

    public CommitBuilder setChangedLinesNumber(int changedLinesNumber) {
        this.changedLinesNumber = changedLinesNumber;
        return this;
    }

    public Commit createCommit() {
        return new Commit(hashCode, author, dateTime, message, deletedLinesNumber, addedLinesNumber, changedLinesNumber);
    }
}