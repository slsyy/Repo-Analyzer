package org.repoanalyzer.statisticsprovider.statistics.commitpercentage;

import org.junit.Before;
import org.junit.Test;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.reporeader.commit.commitsgenerator.CommitsGenerator;

import java.util.List;

import static org.junit.Assert.*;


public class CommitPercentageCalculatorTest {

    private static final String FIRST_AUTHOR_NAME = "first";
    private static final String FIRST_AUTHOR_EMAIL = "first";
    private static final int FIRST_AUTHOR_COMMITS_NUMBER = 100;


    private static final String SECOND_AUTHOR_NAME = "second";
    private static final String SECOND_AUTHOR_EMAIL = "second";
    private static final int SECOND_AUTHOR_COMMITS_NUMBER = 2;


    private static final String THIRD_AUTHOR_NAME = "third";
    private static final String THIRD_AUTHOR_EMAIL = "third";
    private static final int THIRD_AUTHOR_COMMITS_NUMBER = 5000;

    private CommitsGenerator commitsGenerator;
    private CommitPercentageCalculator commitPercentageCalculator;

    @Before
    public void setUp() throws Exception {
        commitsGenerator = new CommitsGenerator();
    }

    private static CommitPercentageData findByAuthorName(List<CommitPercentageData> dataList, String authorName) {
        return dataList.stream()
                .filter(c -> c.getAuthorName().equals(authorName))
                .findFirst()
                .orElse(null);
    }

    @Test
    public void generateData() throws Exception {
        commitsGenerator.createNewTestCommit(FIRST_AUTHOR_COMMITS_NUMBER)
                .setAuthorName(FIRST_AUTHOR_NAME)
                .setAuthorEmail(FIRST_AUTHOR_EMAIL);

        commitsGenerator.createNewTestCommit(SECOND_AUTHOR_COMMITS_NUMBER)
                .setAuthorName(SECOND_AUTHOR_NAME)
                .setAuthorEmail(SECOND_AUTHOR_EMAIL);

        commitsGenerator.createNewTestCommit(THIRD_AUTHOR_COMMITS_NUMBER)
                .setAuthorName(THIRD_AUTHOR_NAME)
                .setAuthorEmail(THIRD_AUTHOR_EMAIL);

        List<Commit> commits = commitsGenerator.getCommits();
        CommitPercentageCalculator commitPercentageCalculator = new CommitPercentageCalculator(commits);
        List<CommitPercentageData> dataList = commitPercentageCalculator.generateData();

        assertEquals(findByAuthorName(dataList, FIRST_AUTHOR_NAME).getAuthorCommitsNumber(), FIRST_AUTHOR_COMMITS_NUMBER);
        assertEquals(findByAuthorName(dataList, SECOND_AUTHOR_NAME).getAuthorCommitsNumber(), SECOND_AUTHOR_COMMITS_NUMBER);
        assertEquals(findByAuthorName(dataList, THIRD_AUTHOR_NAME).getAuthorCommitsNumber(), THIRD_AUTHOR_COMMITS_NUMBER);
    }
}