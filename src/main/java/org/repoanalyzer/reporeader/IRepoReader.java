package org.repoanalyzer.reporeader;

import org.repoanalyzer.reporeader.commit.Commit;

import java.util.List;

public interface IRepoReader {
    List<Commit> getCommits();
    Progress getProgress();
}
