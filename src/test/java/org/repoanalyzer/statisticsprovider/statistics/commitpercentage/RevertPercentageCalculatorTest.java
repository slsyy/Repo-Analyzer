package org.repoanalyzer.statisticsprovider.statistics.commitpercentage;


import org.junit.Before;
import org.junit.Test;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.reporeader.commit.commitsgenerator.CommitsGenerator;
import org.repoanalyzer.statisticsprovider.statistics.revertpercentage.RevertPercentageCalculator;
import org.repoanalyzer.statisticsprovider.statistics.revertpercentage.RevertPercentageData;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RevertPercentageCalculatorTest {
    private static final String FIRST_AUTHOR_NAME = "first";
    private static final String FIRST_AUTHOR_EMAIL = "first";
    private static final int FIRST_AUTHOR_REVERTS_NUMBER = 12;
    private static final int FIRST_AUTHOR_COMMITS_NUMBER = 100;
    private static final Float FIRST_AUTHOR_RATE =
            100*(float)FIRST_AUTHOR_REVERTS_NUMBER / (FIRST_AUTHOR_REVERTS_NUMBER + FIRST_AUTHOR_COMMITS_NUMBER);


    private static final String SECOND_AUTHOR_NAME = "second";
    private static final String SECOND_AUTHOR_EMAIL = "second";
    private static final int SECOND_AUTHOR_REVERTS_NUMBER = 4;
    private static final int SECOND_AUTHOR_COMMITS_NUMBER = 2;
    private static final Float SECOND_AUTHOR_RATE =
            100*(float)SECOND_AUTHOR_REVERTS_NUMBER / (SECOND_AUTHOR_REVERTS_NUMBER + SECOND_AUTHOR_COMMITS_NUMBER);


    private static final String THIRD_AUTHOR_NAME = "third";
    private static final String THIRD_AUTHOR_EMAIL = "third";
    private static final int THIRD_AUTHOR_REVERTS_NUMBER = 1437;
    private static final int THIRD_AUTHOR_COMMITS_NUMBER = 5000;
    private static final Float THIRD_AUTHOR_RATE =
            100*(float)THIRD_AUTHOR_REVERTS_NUMBER / (THIRD_AUTHOR_REVERTS_NUMBER + THIRD_AUTHOR_COMMITS_NUMBER);

    private static final double DELTA = 0.001;

    private CommitsGenerator commitsGenerator;
    private RevertPercentageCalculator revertPercentageCalculator;

    @Before
    public void setUp() throws Exception {
        commitsGenerator = new CommitsGenerator();
    }

    private static RevertPercentageData findByAuthorName(List<RevertPercentageData> dataList, String authorName) {
        return dataList.stream()
                .filter(c -> c.getAuthor().getFirstName().equals(authorName))
                .findFirst()
                .orElse(null);
    }

    @Test
    public void generateData() throws Exception {
        commitsGenerator.createNewTestCommit(FIRST_AUTHOR_COMMITS_NUMBER)
                .setAuthorName(FIRST_AUTHOR_NAME)
                .setAuthorEmail(FIRST_AUTHOR_EMAIL);
        commitsGenerator.createNewTestCommit(FIRST_AUTHOR_REVERTS_NUMBER, "Revert 1")
                .setAuthorName(FIRST_AUTHOR_NAME)
                .setAuthorEmail(FIRST_AUTHOR_EMAIL);

        commitsGenerator.createNewTestCommit(SECOND_AUTHOR_COMMITS_NUMBER)
                .setAuthorName(SECOND_AUTHOR_NAME)
                .setAuthorEmail(SECOND_AUTHOR_EMAIL);
        commitsGenerator.createNewTestCommit(SECOND_AUTHOR_REVERTS_NUMBER, "Revert 2")
                .setAuthorName(SECOND_AUTHOR_NAME)
                .setAuthorEmail(SECOND_AUTHOR_EMAIL);

        commitsGenerator.createNewTestCommit(THIRD_AUTHOR_COMMITS_NUMBER)
                .setAuthorName(THIRD_AUTHOR_NAME)
                .setAuthorEmail(THIRD_AUTHOR_EMAIL);
        commitsGenerator.createNewTestCommit(THIRD_AUTHOR_REVERTS_NUMBER, "Revert 3")
                .setAuthorName(THIRD_AUTHOR_NAME)
                .setAuthorEmail(THIRD_AUTHOR_EMAIL);

        List<Commit> commits = commitsGenerator.getCommits();
        RevertPercentageCalculator revertPercentageCalculator = new RevertPercentageCalculator(commits);
        List<RevertPercentageData> dataList = revertPercentageCalculator.generateStatistics();

        assertEquals(findByAuthorName(dataList, FIRST_AUTHOR_NAME).getPercentage(), FIRST_AUTHOR_RATE, DELTA);
        assertEquals(findByAuthorName(dataList, SECOND_AUTHOR_NAME).getPercentage(), SECOND_AUTHOR_RATE, DELTA);
        assertEquals(findByAuthorName(dataList, THIRD_AUTHOR_NAME).getPercentage(), THIRD_AUTHOR_RATE, DELTA);
    }
}
