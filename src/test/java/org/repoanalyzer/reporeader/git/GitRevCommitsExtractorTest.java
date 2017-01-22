package org.repoanalyzer.reporeader.git;

import org.repoanalyzer.reporeader.exceptions.RepositoryNotFoundOrInvalidException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class GitRevCommitsExtractorTest {
    @Test
    public void getListOfRevCommitsFromValidRepositoryTest() throws Exception {
        //given
        RevCommit revRootCommit = RevCommitGenerator.generateRootCommit();
        RevCommit revCommit = RevCommitGenerator.generateSecondCommit();
        List<RevCommit> expected = new ArrayList<>();
        expected.add(revCommit);
        expected.add(revRootCommit);

        LogCommand log = mock(LogCommand.class);
        given(log.setRevFilter(any(RevFilter.class))).willReturn(log);
        given(log.call()).willReturn(expected);

        Git repo = mock(Git.class);
        given(repo.log()).willReturn(log);

        GitRevCommitsExtractor gitRevCommitsExtractor = new GitRevCommitsExtractor(repo);

        //when
        List<RevCommit> actual = gitRevCommitsExtractor.getListOfRevCommitsFromRepository();

        //then
        assertEquals(expected, actual);
    }

    @Test(expected = RepositoryNotFoundOrInvalidException.class)
    public void getListOfRevCommitsFromBrokenRepositoryTest() throws Exception {
        //given
        LogCommand log = mock(LogCommand.class);
        given(log.setRevFilter(any(RevFilter.class))).willReturn(log);
        given(log.call()).willThrow(new NoHeadException(
                JGitText.get().noHEADExistsAndNoExplicitStartingRevisionWasSpecified));
        Git repo = mock(Git.class);
        given(repo.log()).willReturn(log);
        GitRevCommitsExtractor gitRevCommitsExtractor = new GitRevCommitsExtractor(repo);

        //when
        gitRevCommitsExtractor.getListOfRevCommitsFromRepository();
    }
}
