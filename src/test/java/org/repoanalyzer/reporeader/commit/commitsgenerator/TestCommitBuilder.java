package org.repoanalyzer.reporeader.commit.commitsgenerator;


import org.joda.time.DateTime;
import org.repoanalyzer.reporeader.commit.AuthorProvider;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.reporeader.commit.CommitBuilder;

public class TestCommitBuilder {

    private static final String DEFAULT_AUTHOR_NAME = "DefaultAuthor";
    private static final String DEFAULT_AUTHOR_EMAIL = "aa@bb.cc";
    private static final String DEFAULT_HASH_CODE = "123";
    private static final DateTime DEFAULT_DATE_TIME = new DateTime(0);
    private static final String DEFAULT_EXAMPLE_MESSAGE = "ExampleMessage";

    private AuthorProvider authorProvider;

    private  String authorName;
    private  String authorEmail;
    private  String hashCode;
    private  DateTime dateTime;
    private  String message;
    private int deletedLinesNumber;
    private int addedLinesNumber;
    private int changedLinesNumber;

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

    public TestCommitBuilder setHashCode(String hashCode) {
        this.hashCode = hashCode;
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
        authorName = authorName != null ? authorName : DEFAULT_AUTHOR_NAME;
        authorEmail = authorEmail != null ? authorEmail : DEFAULT_AUTHOR_EMAIL;
        hashCode = hashCode != null ? hashCode : DEFAULT_HASH_CODE;
        dateTime = dateTime != null ? dateTime : DEFAULT_DATE_TIME;
        message = message != null ? message : DEFAULT_EXAMPLE_MESSAGE;

        CommitBuilder commitBuilder = new CommitBuilder(authorProvider, authorName, authorEmail, hashCode, dateTime, message);
        commitBuilder.setChangedLinesNumber(changedLinesNumber).setAddedLinesNumber(addedLinesNumber).setDeletedLinesNumber(deletedLinesNumber);

        return commitBuilder.createCommit();
    }

}
