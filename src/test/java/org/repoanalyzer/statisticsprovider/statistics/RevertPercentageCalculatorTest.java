package org.repoanalyzer.statisticsprovider.statistics;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.repoanalyzer.reporeader.commit.Author;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.statistics.commitsgenerator.CommitsGenerator;
import org.repoanalyzer.statisticsprovider.statistics.revertpercentage.RevertPercentageCalculator;
import org.repoanalyzer.statisticsprovider.statistics.revertpercentage.RevertPercentageData;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class RevertPercentageCalculatorTest {

    private static final double DELTA = 0.01;
    private String authorName;
    private String authorEmail;
    private int revertsNumber;
    private int commitsNumber;
    private double exptected;
    private Set<Author> authors;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {

       return Arrays.asList(new Object[][] {
                { "first", "first@first", 12, 100,  10.714},
                { "second", "second@second", 4, 2,  66.666},
                { "third", "third@third", 1437, 5000,  22.324}
        });
    }

    public RevertPercentageCalculatorTest(String authorName, String authorEmail, int revertsNumber, int commitsNumber,
                                          double expected){
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.revertsNumber = revertsNumber;
        this.commitsNumber = commitsNumber;
        this.exptected = expected;
    }

    @Before
    public void setUp() throws Exception {
        CommitsGenerator commitsGenerator = new CommitsGenerator();

        commitsGenerator.createNewTestCommit(commitsNumber)
                .setAuthorName(authorName)
                .setAuthorEmail(authorEmail);
        commitsGenerator.createNewTestCommit(revertsNumber)
                .setMessage("Revert message")
                .setAuthorName(authorName)
                .setAuthorEmail(authorEmail);

        List<Commit> commits = commitsGenerator.getCommits();
        authors = new HashSet<>();
        Author author = new Author(authorName, authorEmail);
        author.addCommits(commits);
        authors.add(author);
    }

    private static RevertPercentageData findByAuthorName(List<RevertPercentageData> dataList, String authorName) {
        return dataList.stream()
                .filter(c -> c.getAuthor().getFirstName().equals(authorName))
                .findFirst()
                .orElse(null);
    }

    @Test
    public void generateDataRevertPercentageCalculator() throws Exception {

        RevertPercentageCalculator revertPercentageCalculator = new RevertPercentageCalculator(authors);
        List<RevertPercentageData> dataList = revertPercentageCalculator.generateStatistics();

        assertEquals(exptected, findByAuthorName(dataList, authorName).getPercentage(), DELTA);
    }
}
