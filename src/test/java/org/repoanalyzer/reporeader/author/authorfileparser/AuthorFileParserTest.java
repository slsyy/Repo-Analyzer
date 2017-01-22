package org.repoanalyzer.reporeader.author.authorfileparser;

import org.repoanalyzer.reporeader.author.Author;
import org.repoanalyzer.reporeader.exceptions.CannotOpenAuthorFileException;
import org.repoanalyzer.reporeader.exceptions.InvalidJsonDataFormatException;
import org.repoanalyzer.reporeader.exceptions.JsonParsingException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class AuthorFileParserTest {
    private AuthorFileParser authorFileParser;

    @Before
    public void setUp() {
        authorFileParser = new AuthorFileParser();
    }

    @Test
    public void parseFileWithValidDataTest() throws Exception {
        //given
        String testFilePath = getClass().getResource("/TestAuthors.json").getPath();
        Author author1 = new Author("name1");
        Author author2 = new Author("name2", "mail3");

        //when
        author1.addEmailIfNotExists("mail1");
        author1.addEmailIfNotExists("mail2");
        HashSet<Author> actual = authorFileParser.parse(new FileReader(testFilePath));

        //then
        assertEquals(actual.size(), 2);
        assertTrue(actual.stream().anyMatch(author -> author.equals(author1)));
        assertTrue(actual.stream().anyMatch(author -> author.equals(author2)));
    }

    @Test(expected = InvalidJsonDataFormatException.class)
    public void parseFileWithInvalidJsonDataFormatTest() throws Exception {
        //given
        String testFilePath = getClass().getResource("/TestAuthorsBadFormat.json").getPath();

        //when
        authorFileParser.parse(new FileReader(testFilePath));
    }

    @Test(expected = JsonParsingException.class)
    public void parseFileWithInvalidFileDataFormatTest() throws Exception {
        //given
        String testFilePath = getClass().getResource("/TestAuthorsNotJson.json").getPath();

        //when
        authorFileParser.parse(new FileReader(testFilePath));
    }

    @Test(expected = CannotOpenAuthorFileException.class)
    public void parseUnavailableFileTest() throws Exception {
        //given
        FileReader fileReader = mock(FileReader.class);
        given(fileReader.read(any(char[].class), Mockito.anyInt(), Mockito.anyInt())).willThrow(new IOException());

        //when
        authorFileParser.parse(fileReader);
    }
}
