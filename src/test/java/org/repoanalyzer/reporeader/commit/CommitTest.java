package org.repoanalyzer.reporeader.commit;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.repoanalyzer.reporeader.author.Author;

import static org.junit.Assert.assertEquals;

public class CommitTest {
    private Author author;
    private DateTime date;

    @Before
    public void setUp() throws Exception {
        author = new Author("name","email");
        date = new DateTime(0);
    }

    @Test
    public void creationTest() throws Exception {
        // given
        String sha = "sha";
        String message = "message";
        int deletedLinesNumber = 3;
        int addedLinesNumber = 2;
        int changedLinesNumber = 1;

        // when
        Commit commit = new Commit(sha, author, date, message, deletedLinesNumber, addedLinesNumber, changedLinesNumber);

        // then
        assertEquals(commit.getSHA(), sha);
        assertEquals(commit.getAuthor(), author);
        assertEquals(commit.getDateTime(), date);
        assertEquals(commit.getMessage(), message);
        assertEquals(commit.getDeletedLinesNumber(), deletedLinesNumber);
        assertEquals(commit.getAddedLinesNumber(), addedLinesNumber);
        assertEquals(commit.getChangedLinesNumber(), changedLinesNumber);
    }
}
