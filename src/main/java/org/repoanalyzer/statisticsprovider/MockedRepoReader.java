package org.repoanalyzer.statisticsprovider;


import org.joda.time.DateTime;
import org.repoanalyzer.reporeader.AbstractRepoReader;
import org.repoanalyzer.reporeader.commit.Author;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.reporeader.commit.CommitBuilder;

import java.util.Collections;
import java.util.List;


public class MockedRepoReader extends AbstractRepoReader {

    private final Commit commit;

    public MockedRepoReader(String url) {
        super(url);
        commit = new CommitBuilder().
                setHashCode("sfnsofsf").
                setAuthor(new Author("Ktos tam", "ktos@tam")).
                setDate(new DateTime("2004-12-13T21:39:45.618-08:00")).
                setMessage("Sample commit").
                setDeletedLinesNumber(5).
                setAddedLinesNumber(100).
                setChangedLinesNumber(5).
                createCommit();
    }

    public List<Commit> getCommits() {
        return Collections.singletonList(commit);
    }
}
