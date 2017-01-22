package org.repoanalyzer.reporeader.git;


import org.junit.Test;
import org.repoanalyzer.reporeader.exceptions.RepositoryNotFoundOrInvalidException;

public class GitRevCommitsExtractorTest {
    @Test
    public void getListOfRevCommitsFromValidRepositoryTest() throws Exception {
        //TODO: MOCKS!
    }

    @Test(expected = RepositoryNotFoundOrInvalidException.class)
    public void getListOfRevCommitsFromBrokenRepositoryTest() throws Exception {
        //TODO: MOCKS!
    }
}
