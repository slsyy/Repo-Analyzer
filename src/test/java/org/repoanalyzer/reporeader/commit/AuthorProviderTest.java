package org.repoanalyzer.reporeader.commit;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AuthorProviderTest {
    private AuthorProvider authorProvider;
    private Author author;

    @Before
    public void setUp() throws Exception {
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
        assertEquals(author.getNames().size(), 1);
        assertTrue(author.getNames().contains(name));
        assertEquals(author.getEmails().size(), 1);
        assertTrue(author.getEmails().contains(mail));
    }

    @Test
    public void createAuthorWithOneNameAndTwoEmailsTest() throws Exception {
        //given
        String name = "name";
        String mail1 = "email1";
        String mail2 = "email2";

        //when
        author = authorProvider.getCreateOrUpdateAuthor(name, mail1);
        author = authorProvider.getCreateOrUpdateAuthor(name, mail2);

        //then
        assertEquals(author.getNames().size(), 1);
        assertTrue(author.getNames().contains(name));
        assertEquals(author.getEmails().size(), 2);
        assertTrue(author.getEmails().contains(mail1));
        assertTrue(author.getEmails().contains(mail2));
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
        assertEquals(author.getNames().size(), 2);
        assertTrue(author.getNames().contains(name1));
        assertTrue(author.getNames().contains(name2));
        assertEquals(author.getEmails().size(), 1);
        assertTrue(author.getEmails().contains(mail));
    }

    @Test
    public void addAuthorsFromFileTest() throws Exception {
        //given
        String testFilePath = System.getProperty("user.dir")
                + "\\src\\test\\java\\org\\repoanalyzer\\reporeader\\commit\\TestAuthors.json";
        String author1 = "[name2]:[mail3]";
        String author2 = "[name1]:[mail1, mail2]";

        //when
        authorProvider.addAuthorsFromFile(testFilePath);

        //then
        assertEquals(authorProvider.getAuthorsSize(),2);
        assertTrue(authorProvider.doesSetAsStringContainsAuthor(author1));
        assertTrue(authorProvider.doesSetAsStringContainsAuthor(author2));
    }
}
