package org.repoanalyzer.reporeader.author;

import org.junit.Test;
import org.repoanalyzer.reporeader.exceptions.CannotOpenAuthorFileException;
import org.repoanalyzer.reporeader.exceptions.InvalidJsonDataFormatException;
import org.repoanalyzer.reporeader.exceptions.JsonParsingException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FilePreloadedAuthorProviderTest {
    private AuthorProvider authorProvider;

    @Test
    public void addAuthorsFromFileTest() throws Exception {
        //given
        String testFilePath = getClass().getResource("/TestAuthors.json").getPath();
        Author author1 = new Author("name2", "mail3");
        Author author2 = new Author("name1");
        author2.addEmailIfNotExists("mail1");
        author2.addEmailIfNotExists("mail2");

        //when
        authorProvider = new FilePreloadedAuthorProvider(testFilePath);

        //then
        assertEquals(authorProvider.getAuthorsNumber(), 2);
        assertTrue(authorProvider.doesAuthorExistInList(author1));
        assertTrue(authorProvider.doesAuthorExistInList(author2));
    }

    @Test(expected = CannotOpenAuthorFileException.class)
    public void cannotOpenAuthorFileExceptionTest() throws Exception {
        authorProvider = new FilePreloadedAuthorProvider("/TestAuthors1.json");
    }

    @Test(expected = InvalidJsonDataFormatException.class)
    public void invalidJsonDataFormatExceptionTest() throws Exception {
        String testFilePath = getClass().getResource("/TestAuthorsBadFormat.json").getPath();
        authorProvider = new FilePreloadedAuthorProvider(testFilePath);
    }

    @Test(expected = JsonParsingException.class)
    public void jsonParsingExceptionTest() throws Exception {
        String testFilePath = getClass().getResource("/TestAuthorsNotJson.json").getPath();
        authorProvider = new FilePreloadedAuthorProvider(testFilePath);
    }
}
