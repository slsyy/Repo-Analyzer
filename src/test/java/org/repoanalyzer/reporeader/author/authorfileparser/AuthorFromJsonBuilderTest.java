package org.repoanalyzer.reporeader.author.authorfileparser;

import org.json.simple.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.repoanalyzer.reporeader.author.Author;
import org.repoanalyzer.reporeader.exceptions.InvalidJsonDataFormatException;

import static org.junit.Assert.assertEquals;

public class AuthorFromJsonBuilderTest {
    private String name = "name";
    private AuthorFromJsonBuilder authorBuilder;

    @Before
    public void setUp() {
        authorBuilder = new AuthorFromJsonBuilder(name);
    }

    @Test
    public void buildValidAuthorTest() throws Exception {
        //given
        String email1 = "email1";
        String email2 = "email2";
        JSONArray emails = new JSONArray();
        Author expected = new Author(name);

        //when
        expected.addEmailIfNotExists(email1);
        expected.addEmailIfNotExists(email2);
        emails.add(email1);
        emails.add(email2);

        Author actual = authorBuilder.addJsonArrayOfEmails(emails).build();

        //then
        assertEquals(expected,actual);
    }

    @Test(expected = InvalidJsonDataFormatException.class)
    public void buildAuthorWithInvalidEmailsArrayTest() throws Exception {
        //given
        String email1 = "email1";
        String email2 = "email2";
        JSONArray emails = new JSONArray();
        JSONArray innerEmails = new JSONArray();
        Author expected = new Author(name);

        //when
        expected.addEmailIfNotExists(email1);
        expected.addEmailIfNotExists(email2);
        innerEmails.add(email1);
        innerEmails.add(email2);
        emails.add(email1);
        emails.add(innerEmails);

        authorBuilder.addJsonArrayOfEmails(emails).build();
    }
}
