package org.repoanalyzer.statisticsprovider.statistics.commitsgenerator;


import org.joda.time.DateTime;
import org.repoanalyzer.reporeader.author.AuthorProvider;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.reporeader.commit.CommitBuilder;
import org.repoanalyzer.reporeader.exceptions.IncompleteCommitInfoException;

public class TestCommitBuilder {

    private AuthorProvider authorProvider;

    private  String authorName = "DefaultAuthor";
    private  String authorEmail = "aa@bb.cc";
    private  String sha = "123";
    private  DateTime dateTime = new DateTime(0);
    private  String message = "ExampleMessage";
    private int deletedLinesNumber = 1;
    private int addedLinesNumber = 1;
    private int changedLinesNumber = 1;

    public TestCommitBuilder(AuthorProvider authorProvider){
        this.authorProvider = authorProvider;
    }

    public TestCommitBuilder setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public TestCommitBuilder setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
        return this;
    }

    public TestCommitBuilder setSHA(String hashCode) {
        this.sha = sha;
        return this;
    }

    public TestCommitBuilder setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public TestCommitBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public TestCommitBuilder setDeletedLinesNumber(int deletedLinesNumber) {
        this.deletedLinesNumber = deletedLinesNumber;
        return this;
    }

    public TestCommitBuilder setAddedLinesNumber(int addedLinesNumber) {
        this.addedLinesNumber = addedLinesNumber;
        return this;
    }

    public TestCommitBuilder setChangedLinesNumber(int changedLinesNumber) {
        this.changedLinesNumber = changedLinesNumber;
        return this;
    }

    public Commit createCommit() {
        CommitBuilder commitBuilder = new CommitBuilder(authorProvider);

        try {
            return commitBuilder.setAuthorName(authorName)
                                .setAuthorEmail(authorEmail)
                                .setSHA(sha)
                                .setDateTime(dateTime)
                                .setMessage(message)
                                .setChangedLinesNumber(changedLinesNumber)
                                .setAddedLinesNumber(addedLinesNumber)
                                .setDeletedLinesNumber(deletedLinesNumber)
                                .createCommit();
        } catch (IncompleteCommitInfoException e) {
            return null;
        }
    }

}
