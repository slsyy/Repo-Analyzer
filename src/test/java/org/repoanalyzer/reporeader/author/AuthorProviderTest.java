package org.repoanalyzer.reporeader.author;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AuthorProviderTest {
    private AuthorProvider authorProvider;
    private Author author;

    @Before
    public void setUp() {
        authorProvider = new AuthorProvider();
    }

    @Test
    public void createAuthorWithOneNameAndEmailTest() throws Exception {
        //given
        String name = "name";
        String mail = "email";

        //when
        author = authorProvider.getCreateOrUpdateAuthor(name, mail);

        //then
        assertEquals(author.getNamesNumber(), 1);
        assertTrue(author.containsName(name));
        assertEquals(author.getEmailsNumber(), 1);
        assertTrue(author.containsEmail(mail));
    }

    @Test
    public void createAuthorWithOneNameAndTwoEmailsTest() throws Exception {
        //given
        String name = "name";
        String mail1 = "email1";
        String mail2 = "email2";

        //when
        authorProvider.getCreateOrUpdateAuthor(name, mail1);
        author = authorProvider.getCreateOrUpdateAuthor(name, mail2);

        //then
        assertEquals(author.getNamesNumber(), 1);
        assertTrue(author.containsName(name));
        assertEquals(author.getEmailsNumber(), 2);
        assertTrue(author.containsEmail(mail1));
        assertTrue(author.containsEmail(mail2));
    }

    @Test
    public void createAuthorWithTwoNamesAndOneEmailTest() throws Exception {
        //given
        String name1 = "name1";
        String name2 = "name2";
        String mail = "email";

        //when
        author = authorProvider.getCreateOrUpdateAuthor(name1, mail);
        author = authorProvider.getCreateOrUpdateAuthor(name2, mail);

        //then
        assertEquals(author.getNamesNumber(), 2);
        assertTrue(author.containsName(name1));
        assertTrue(author.containsName(name2));
        assertEquals(author.getEmailsNumber(), 1);
        assertTrue(author.containsEmail(mail));
    }

    @Test
    public void checkIfAuthorProviderContainsAuthorBeforeCreationTest() throws Exception {
        //given
        String name = "name";
        String mail = "email";
        Author expected = new Author(name, mail);

        //when

        //then
        assertFalse(authorProvider.doesAuthorExistsInList(expected));
    }
}
