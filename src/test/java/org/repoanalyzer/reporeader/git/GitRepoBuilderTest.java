package org.repoanalyzer.reporeader.git;

import org.junit.Test;
import org.repoanalyzer.reporeader.exceptions.RepositoryNotFoundOrInvalidException;

import java.io.File;

public class GitRepoBuilderTest {
    @Test
    public void buildRepoFromValidPathTest() throws Exception {
        //given
        String testPath = getClass().getResource("/TestAuthors.json").getPath();
        File file = new File(testPath);
        while(!testPath.endsWith("Repo-Analyzer")) {
            file = file.getParentFile();
            testPath = file.getPath();
        }
        testPath += "/.git";

        GitRepoBuilder gitRepoBuilder = new GitRepoBuilder(testPath);

        //when
        gitRepoBuilder.build();
    }

    @Test(expected = RepositoryNotFoundOrInvalidException.class)
    public void buildRepoFromInvalidPathTest() throws Exception {
        //given
        String testPath = getClass().getResource("/TestAuthors.json").getPath();
        GitRepoBuilder gitRepoBuilder = new GitRepoBuilder(testPath);

        //when
        gitRepoBuilder.build();
    }
}
