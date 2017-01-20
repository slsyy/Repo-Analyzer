package org.repoanalyzer.reporeader.commit;

import org.repoanalyzer.reporeader.author.Author;
import org.repoanalyzer.reporeader.author.AuthorProvider;
import org.repoanalyzer.reporeader.exceptions.IncompleteCommitInfoException;

import org.joda.time.DateTime;

public class CommitBuilder {
    private final AuthorProvider authorProvider;
    private String authorName;
    private String authorEmail;
    private String sha;
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

    public CommitBuilder setSHA(String sha) {
        this.sha = sha;
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
        if (this.authorName.isEmpty()) throw new IncompleteCommitInfoException("Missing author name.");
        if (this.authorEmail.isEmpty()) throw new IncompleteCommitInfoException("Missing author email.");

        Author author = this.authorProvider.getCreateOrUpdateAuthor(this.authorName, this.authorEmail);

        if (this.sha.isEmpty()) throw new IncompleteCommitInfoException("Missing commit hashCode.");
        if (this.dateTime.isEqual(new DateTime(0))) throw new IncompleteCommitInfoException("Missing commit date.");

        Commit commit = new Commit(this.sha, author, this.dateTime, this.message, this.deletedLinesNumber, this.addedLinesNumber, this.changedLinesNumber);
        author.addCommit(commit);
        return commit;
    }
}