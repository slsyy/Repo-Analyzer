package org.repoanalyzer.reporeader.commit;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

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
        String hash = "hash";
        String message = "message";
        int deletedLinesNumber = 3;
        int addedLinesNumber = 2;
        int changedLinesNumber = 1;

        // when
        Commit commit = new Commit(hash, author, date, message, deletedLinesNumber, addedLinesNumber, changedLinesNumber);

        // then
        assertEquals(commit.getHashCode(), hash);
        assertEquals(commit.getAuthor(), author);
        assertEquals(commit.getDateTime(), date);
        assertEquals(commit.getMessage(), message);
        assertEquals(commit.getDeletedLinesNumber(), deletedLinesNumber);
        assertEquals(commit.getAddedLinesNumber(), addedLinesNumber);
        assertEquals(commit.getChangedLinesNumber(), changedLinesNumber);
    }

    @Test
    public void commitToStringTest() throws Exception {
        //given

        //when
        Commit commit = new Commit("hash", author, date, "message", 0, 0, 0);

        //then
        assertEquals(commit.toString(),
                "Commit{hashCode='hash', author=name, dateTime=1970-01-01T01:00:00.000+01:00, message='message'"
                        + ", deletedLinesNumber=0, addedLinesNumber=0, changedLinesNumber=0}"
        );
    }
}
