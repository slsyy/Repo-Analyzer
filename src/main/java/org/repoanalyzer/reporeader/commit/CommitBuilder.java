package org.repoanalyzer.reporeader.commit;

import org.joda.time.DateTime;

public class CommitBuilder {
    private final Author author;
    private final String hashCode;
    private final DateTime dateTime;
    private final String message;
    private int deletedLinesNumber;
    private int addedLinesNumber;
    private int changedLinesNumber;

    public CommitBuilder(AuthorProvider authorProvider,
                         String authorName,
                         String authorEmail,
                         String hashCode,
                         DateTime dateTime,
                         String message){
        this.author = authorProvider.getCreateOrUpdateAuthor(authorName, authorEmail);
        this.hashCode = hashCode;
        this.dateTime = dateTime;
        this.message = message;
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
        Commit commit = new Commit(hashCode, author, dateTime, message, deletedLinesNumber, addedLinesNumber, changedLinesNumber);
        author.addCommit(commit);
        return commit;
    }
}