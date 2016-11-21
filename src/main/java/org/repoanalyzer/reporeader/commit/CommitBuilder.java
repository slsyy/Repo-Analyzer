package org.repoanalyzer.reporeader.commit;

import org.joda.time.DateTime;

public class CommitBuilder {
    private String hashCode;
    private Author author;
    private DateTime dateTime;
    private String message;
    private int deletedLinesNumber;
    private int addedLinesNumber;
    private int changedLinesNumber;

    public CommitBuilder setHashCode(String hashCode) {
        this.hashCode = hashCode;
        return this;
    }

    public CommitBuilder setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public CommitBuilder setDate(DateTime date) {
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