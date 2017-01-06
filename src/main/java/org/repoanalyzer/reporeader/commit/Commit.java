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
        return hashCode;
    }

    public Author getAuthor() {
        return author;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public String getMessage() {
        return message;
    }

    public int getDeletedLinesNumber() {
        return deletedLinesNumber;
    }

    public int getAddedLinesNumber() {
        return addedLinesNumber;
    }

    public int getChangedLinesNumber() {
        return changedLinesNumber;
    }

    @Override
    public String toString() {
        return "Commit{" +
                "hashCode='" + hashCode + '\'' +
                ", author=" + author.getFirstName() +
                ", dateTime=" + dateTime +
                ", message='" + message + '\'' +
                ", deletedLinesNumber=" + deletedLinesNumber +
                ", addedLinesNumber=" + addedLinesNumber +
                ", changedLinesNumber=" + changedLinesNumber +
                '}';
    }
}
