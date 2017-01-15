package org.repoanalyzer.reporeader.commit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FilePreloadedAuthorProviderTest {
    private AuthorProvider authorProvider;

    @Test
    public void addAuthorsFromFileTest() throws Exception {
        //given
        String testFilePath = System.getProperty("user.dir")
                + "/src/test/java/org/repoanalyzer/reporeader/commit/TestAuthors.json";
        String author1 = "[name2]:[mail3]";
        String author2 = "[name1]:[mail1, mail2]";

        //when
        try {
            authorProvider = new FilePreloadedAuthorProvider(testFilePath);
        } catch (Exception e) {
            authorProvider = new AuthorProvider();
        }

        //then
        assertEquals(authorProvider.getAuthorsNumber(),2);
        assertTrue(authorProvider.doesSetAsStringContainsAuthor(author1));
        assertTrue(authorProvider.doesSetAsStringContainsAuthor(author2));
    }
}
