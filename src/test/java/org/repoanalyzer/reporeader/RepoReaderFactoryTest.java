package org.repoanalyzer.reporeader;

import org.junit.Before;
import org.junit.Test;
import org.repoanalyzer.reporeader.author.AuthorProvider;
import org.repoanalyzer.reporeader.exceptions.AuthorProviderMustExistsException;
import org.repoanalyzer.reporeader.exceptions.RepositoryNotFoundOrInvalidException;

public class RepoReaderFactoryTest {
    RepoReaderFactory repoReaderFactory;

    @Before
    public void setUp() {
        this.repoReaderFactory = new RepoReaderFactory();
    }

    @Test
    public void createValidRepository() throws Exception {
        //given

        //when
        this.repoReaderFactory.create(".git", new AuthorProvider());
    }

    @Test(expected = AuthorProviderMustExistsException.class)
    public void createRepositoryWithNoAuthorProvider() throws Exception {
        //given

        //when
        this.repoReaderFactory.create(".git", null);
    }

    @Test(expected = RepositoryNotFoundOrInvalidException.class)
    public void createUnsupportedRepositoryType() throws Exception {
        //given

        //when
        this.repoReaderFactory.create("B==D", new AuthorProvider());
    }
}
