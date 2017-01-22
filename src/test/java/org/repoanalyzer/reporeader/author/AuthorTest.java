package org.repoanalyzer.reporeader.author;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AuthorTest {
    @Test
    public void equalTest() throws Exception {
        //given
        Author author1 = new Author("name", "email");
        Author author2 = new Author("name", "email");

        //then
        assertTrue(author1.equals(author2));
    }

    @Test
    public void unequalTest() throws Exception {
        //given
        Author author1 = new Author("name1", "email1");
        Author author2 = new Author("name2", "email2");

        //then
        assertFalse(author1.equals(author2));
    }

    @Test
    public void compareAuthorWithStringTest() throws Exception {
        //given
        Author author = new Author("name", "email");
        String name = "[name]:[email]";

        //then
        assertFalse(author.equals(name));
    }
}
