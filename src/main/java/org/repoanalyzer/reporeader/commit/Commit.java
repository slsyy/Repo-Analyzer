package org.repoanalyzer.reporeader.commit;

import org.joda.time.DateTime;

final public class Commit {

    private final String hashCode;
    private final Author author;
    private final DateTime dateTime;
    private final String message;
    private final int deletedLinesNumber;
    private final int addedLinesNumber;
    private final int changedLinesNumber;

    Commit(String hashCode, Author author, DateTime dateTime, String message, int deletedLinesNumber, int addedLinesNumber, int changedLinesNumber) {
        this.hashCode = hashCode;
        this.author = author;
        this.dateTime = dateTime;
        this.message = message;
        this.deletedLinesNumber = deletedLinesNumber;
        this.addedLinesNumber = addedLinesNumber;
        this.changedLinesNumber = changedLinesNumber;
    }

    public String getHashCode() {
        return this.hashCode;
    }

    public Author getAuthor() {
        return this.author;
    }

    public DateTime getDateTime() {
        return this.dateTime;
    }

    public String getMessage() {
        return this.message;
    }

    public int getDeletedLinesNumber() {
        return this.deletedLinesNumber;
    }

    public int getAddedLinesNumber() {
        return this.addedLinesNumber;
    }

    public int getChangedLinesNumber() {
        return this.changedLinesNumber;
    }

    @Override
    public String toString() {
        return "Commit{" +
                "hashCode='" + this.hashCode + '\'' +
                ", author=" + this.author.getFirstName() +
                ", dateTime=" + this.dateTime +
                ", message='" + this.message + '\'' +
                ", deletedLinesNumber=" + this.deletedLinesNumber +
                ", addedLinesNumber=" + this.addedLinesNumber +
                ", changedLinesNumber=" + this.changedLinesNumber +
                '}';
    }
}
