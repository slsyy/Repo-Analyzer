package org.repoanalyzer.reporeader.git;

import org.junit.Test;
import org.repoanalyzer.reporeader.Progress;
import org.repoanalyzer.reporeader.author.Author;
import org.repoanalyzer.reporeader.author.AuthorProvider;
import org.repoanalyzer.reporeader.commit.Commit;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

import static junit.framework.TestCase.assertEquals;

public class GitRepoReaderTest {
//TODO: Try to mock it, but it may be hard to test GitRepoReader:getCommits()
    @Test
    public void getCommitsTest() throws Exception {
        //given
        String testPath = getClass().getResource("/TestAuthors.json").getPath();
        while(!testPath.endsWith("Repo-Analyzer"))
            testPath = testPath.substring(0,testPath.lastIndexOf('/'));
        testPath += "/.git";
        AuthorProvider authorProvider = new AuthorProvider();
        GitRepoReader gitRepoReader = new GitRepoReader(testPath, authorProvider);

        //when
        Future<List<Commit>> commits = gitRepoReader.getCommits();

        //then
        while(!commits.isDone());
    }

    @Test
    public void getProgressTest() throws Exception {
        //given
        GitRepoReader gitRepoReader = new GitRepoReader("url", null);

        //when
        Progress progress = gitRepoReader.getProgress();

        //then
        assertEquals(progress.getMax(), 0);
        assertEquals(progress.getWorkDone(), 0);
    }

    @Test
    public void getAuthorsTest() throws Exception {
        //given
        String name = "name";
        String email = "email";
        Author author = new Author(name, email);
        AuthorProvider authorProvider = new AuthorProvider();
        authorProvider.getCreateOrUpdateAuthor(name, email);
        GitRepoReader gitRepoReader = new GitRepoReader("url", authorProvider);

        //when
        Set<Author> authors = gitRepoReader.getAuthors();

        //then
        assertEquals(authors.size(), 1);
        assertEquals(authors.toArray()[0], author);
    }
}
