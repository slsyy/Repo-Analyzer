package org.repoanalyzer.reporeader.commit;

import org.joda.time.DateTime;
import org.repoanalyzer.reporeader.exceptions.IncompleteCommitInfoException;

public class CommitBuilder {
    private final AuthorProvider authorProvider;
    private String authorName;
    private String authorEmail;
    private String hashCode;
    private String message;
    private DateTime dateTime;
    private int deletedLinesNumber;
    private int addedLinesNumber;
    private int changedLinesNumber;

    public CommitBuilder(AuthorProvider authorProvider){
        this.authorProvider = authorProvider;
    }

    public CommitBuilder setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public CommitBuilder setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
        return this;
    }

    public CommitBuilder setHashCode(String hashCode) {
        this.hashCode = hashCode;
        return this;
    }

    public CommitBuilder setDateTime(DateTime dateTime) {
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

    public Commit createCommit() throws IncompleteCommitInfoException{
        if (authorName.isEmpty()) throw new IncompleteCommitInfoException("Missing author name.");
        if (authorEmail.isEmpty()) throw new IncompleteCommitInfoException("Missing author email.");

        Author author = authorProvider.getCreateOrUpdateAuthor(authorName, authorEmail);

        if (hashCode.isEmpty()) throw new IncompleteCommitInfoException("Missing commit hashCode.");
        if (dateTime.isEqual(new DateTime(0))) throw new IncompleteCommitInfoException("Missing commit date.");

        Commit commit = new Commit(hashCode, author, dateTime, message, deletedLinesNumber, addedLinesNumber, changedLinesNumber);
        author.addCommit(commit);
        return commit;
    }
}