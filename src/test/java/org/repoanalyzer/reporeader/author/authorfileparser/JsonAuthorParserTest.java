package org.repoanalyzer.reporeader.author.authorfileparser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.repoanalyzer.reporeader.author.Author;
import org.repoanalyzer.reporeader.exceptions.InvalidJsonDataFormatException;

import static org.junit.Assert.assertEquals;

public class JsonAuthorParserTest {
    private JsonAuthorParser jsonAuthorParser;

    @Before
    public void setUp() {
        this.jsonAuthorParser = new JsonAuthorParser();
    }

    @Test
    public void parseValidJsonAuthorTest() throws Exception {
        //given
        String name = "name";
        String email1 = "email1";
        String email2 = "email2";
        Author expected = new Author(name);
        JSONObject authorToParse = new JSONObject();
        JSONArray emails = new JSONArray();

        //when
        expected.addEmailIfNotExists(email1);
        expected.addEmailIfNotExists(email2);
        emails.add(email1);
        emails.add(email2);
        authorToParse.put(name,emails);

        Author actual = this.jsonAuthorParser.parse(authorToParse);

        //then
        assertEquals(actual, expected);
    }

    @Test(expected = InvalidJsonDataFormatException.class)
    public void parseInvalidJsonAuthorWithoutEmailArrayTest() throws Exception {
        //given
        String name = "name";
        String email = "email";
        JSONObject authorToParse = new JSONObject();

        //when
        authorToParse.put(name,email);

        this.jsonAuthorParser.parse(authorToParse);
    }

    @Test(expected = InvalidJsonDataFormatException.class)
    public void parseInvalidJsonAuthorWithMoreElementsInMapTest() throws Exception {
        //given
        String name1 = "name1";
        String name2 = "name2";
        String email1 = "email1";
        String email2 = "email2";
        JSONObject authorToParse = new JSONObject();
        JSONArray emails = new JSONArray();

        //when
        emails.add(email1);
        authorToParse.put(name1,emails);
        emails.clear();
        emails.add(email2);
        authorToParse.put(name2,emails);

        this.jsonAuthorParser.parse(authorToParse);
    }

    @Test(expected = InvalidJsonDataFormatException.class)
    public void parseInvalidJsonAuthorWithArrayAsMapKeyTest() throws Exception {
        //given
        String name1 = "name1";
        String name2 = "name2";
        String email1 = "email1";
        String email2 = "email2";
        JSONObject authorToParse = new JSONObject();
        JSONArray names = new JSONArray();
        JSONArray emails = new JSONArray();

        //when
        names.add(name1);
        names.add(name2);
        emails.add(email1);
        emails.add(email2);
        authorToParse.put(names,emails);

        this.jsonAuthorParser.parse(authorToParse);
    }
}
