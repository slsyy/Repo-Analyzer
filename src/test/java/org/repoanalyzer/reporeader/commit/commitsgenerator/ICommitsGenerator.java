package org.repoanalyzer.reporeader.commit.commitsgenerator;

import org.repoanalyzer.reporeader.commit.Commit;

import java.util.List;

public interface ICommitsGenerator {
    List<Commit> getCommits();
}
