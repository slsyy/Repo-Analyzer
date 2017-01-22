package org.repoanalyzer.reporeader.commit;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.repoanalyzer.reporeader.author.Author;
import org.repoanalyzer.reporeader.author.AuthorProvider;
import org.repoanalyzer.reporeader.exceptions.IncompleteCommitInfoException;

import static junit.framework.TestCase.assertEquals;

public class CommitBuilderTest {
    private CommitBuilder commitBuilder;
    private String name = "name";
    private String mail = "mail";
    private String sha = "Commit hash";
    private DateTime date = new DateTime(2017,1,21,21,41);
    private String message = "Commit message";
    private int deletedLinesNumber = 14;
    private int addedLinesNumber = 19;
    private int changedLinesNumber = 5;

    @Before
    public void setUp() {
        commitBuilder = new CommitBuilder(new AuthorProvider());
    }

    @Test
    public void createValidCommitTest() throws Exception {
        //given

        //when
        Author author = new Author(name, mail);
        Commit actual = commitBuilder.setAuthorName(name)
                .setAuthorEmail(mail)
                .setSHA(sha)
                .setDateTime(date)
                .setMessage(message)
                .setAddedLinesNumber(addedLinesNumber)
                .setDeletedLinesNumber(deletedLinesNumber)
                .setChangedLinesNumber(changedLinesNumber)
                .createCommit();

        //then
        assertEquals(actual.getAuthor(), author);
        assertEquals(actual.getSHA(), sha);
        assertEquals(actual.getDateTime(), date);
        assertEquals(actual.getMessage(), message);
        assertEquals(actual.getAddedLinesNumber(), addedLinesNumber);
        assertEquals(actual.getDeletedLinesNumber(), deletedLinesNumber);
        assertEquals(actual.getChangedLinesNumber(), changedLinesNumber);
    }

    @Test(expected = IncompleteCommitInfoException.class)
    public void createInvalidCommitWithoutAuthorNameTest() throws Exception {
        //given

        //when
        commitBuilder.setAuthorEmail(mail)
                .setSHA(sha)
                .setDateTime(date)
                .setMessage(message)
                .setAddedLinesNumber(addedLinesNumber)
                .setDeletedLinesNumber(deletedLinesNumber)
                .setChangedLinesNumber(changedLinesNumber)
                .createCommit();
    }

    @Test(expected = IncompleteCommitInfoException.class)
    public void createInvalidCommitWithoutAuthorMailTest() throws Exception {
        //given

        //when
        commitBuilder.setAuthorName(name)
                     .setSHA(sha)
                     .setDateTime(date)
                     .setMessage(message)
                     .setAddedLinesNumber(addedLinesNumber)
                     .setDeletedLinesNumber(deletedLinesNumber)
                     .setChangedLinesNumber(changedLinesNumber)
                     .createCommit();
    }

    @Test(expected = IncompleteCommitInfoException.class)
    public void createInvalidCommitWithoutShaTest() throws Exception {
        //given

        //when
        commitBuilder.setAuthorName(name)
                .setAuthorEmail(mail)
                .setDateTime(date)
                .setMessage(message)
                .setAddedLinesNumber(addedLinesNumber)
                .setDeletedLinesNumber(deletedLinesNumber)
                .setChangedLinesNumber(changedLinesNumber)
                .createCommit();
    }

    @Test(expected = IncompleteCommitInfoException.class)
    public void createInvalidCommitWithoutDateTest() throws Exception {
        //given

        //when
        commitBuilder.setAuthorName(name)
                .setAuthorEmail(mail)
                .setSHA(sha)
                .setMessage(message)
                .setAddedLinesNumber(addedLinesNumber)
                .setDeletedLinesNumber(deletedLinesNumber)
                .setChangedLinesNumber(changedLinesNumber)
                .createCommit();
    }

    @Test(expected = IncompleteCommitInfoException.class)
    public void createInvalidCommitWithoutMessageTest() throws Exception {
        //given

        //when
        commitBuilder.setAuthorName(name)
                .setAuthorEmail(mail)
                .setSHA(sha)
                .setDateTime(date)
                .setAddedLinesNumber(addedLinesNumber)
                .setDeletedLinesNumber(deletedLinesNumber)
                .setChangedLinesNumber(changedLinesNumber)
                .createCommit();
    }

    @Test(expected = IncompleteCommitInfoException.class)
    public void createInvalidCommitWithoutLaneChangeTest() throws Exception {
        //given
        this.deletedLinesNumber = 0;
        this.addedLinesNumber = 0;
        this.changedLinesNumber = 0;

        //when
        commitBuilder.setAuthorName(name)
                .setAuthorEmail(mail)
                .setSHA(sha)
                .setDateTime(date)
                .setMessage(message)
                .setAddedLinesNumber(addedLinesNumber)
                .setDeletedLinesNumber(deletedLinesNumber)
                .setChangedLinesNumber(changedLinesNumber)
                .createCommit();
    }
}
