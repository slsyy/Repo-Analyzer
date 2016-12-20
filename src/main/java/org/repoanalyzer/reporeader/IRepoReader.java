package org.repoanalyzer.reporeader;

import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.reporeader.exceptions.RepositoryNotFoundOrInvalidException;

import java.util.List;
import java.util.concurrent.Future;

public interface IRepoReader {
    Future<List<Commit>> getCommits() throws RepositoryNotFoundOrInvalidException;
    Progress getProgress();
}
