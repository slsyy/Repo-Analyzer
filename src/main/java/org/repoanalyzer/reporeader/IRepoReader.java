package org.repoanalyzer.reporeader;

import org.repoanalyzer.reporeader.commit.Commit;

import java.util.List;
import java.util.concurrent.Future;

public interface IRepoReader {
    Future<List<Commit>> getCommits();
    Progress getProgress();
}
