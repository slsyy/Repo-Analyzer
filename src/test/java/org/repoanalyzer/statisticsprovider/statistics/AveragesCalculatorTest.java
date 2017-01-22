package org.repoanalyzer.statisticsprovider.statistics;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.repoanalyzer.reporeader.author.Author;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.statistics.averages.AveragesCalculator;
import org.repoanalyzer.statisticsprovider.statistics.averages.AveragesData;
import org.repoanalyzer.statisticsprovider.statistics.commitsgenerator.CommitsGenerator;

import java.util.*;

import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class AveragesCalculatorTest {

    private static final double DELTA = 1.0;
    private String authorName;
    private String authorEmail;
    private double expectedAdded;
    private double expectedDeleted;
    private double expectedChanged;
    private int[] addedDeletedChanged;
    private Set<Author> authors;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "first", "first@first", 33.0,  8.0, 17.0, new int[]{25, 2, 21, 42, 12, 14, 33, 10, 17}},
                { "second", "second@second", 13.0, 23.0, 21.0 ,new int[]{10, 2, 7, 30, 50, 50, 0, 18, 6}},
                { "third", "third@third", 486.0, 157.0, 698.0, new int[]{507, 204, 804, 930, 247, 1240, 21, 20, 51}}
        });
    }

    public AveragesCalculatorTest(String authorName, String authorEmail, double expectedAdded, double expectedDeleted,
                                          double expectedChanged, int[] args){
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.expectedAdded = expectedAdded;
        this.expectedDeleted = expectedDeleted;
        this.expectedChanged = expectedChanged;
        this.addedDeletedChanged = args;
    }

    @Before
    public void setUp() throws Exception {
        CommitsGenerator commitsGenerator = new CommitsGenerator();

        for(int i = 0; i < addedDeletedChanged.length; i += 3){
            commitsGenerator.createNewTestCommit()
                    .setAuthorName(authorName)
                    .setAuthorEmail(authorEmail)
                    .setAddedLinesNumber(addedDeletedChanged[i])
                    .setDeletedLinesNumber(addedDeletedChanged[i+1])
                    .setChangedLinesNumber(addedDeletedChanged[i+2]);
        }

        List<Commit> commits = commitsGenerator.getCommits();
        authors = new HashSet<>();
        Author author = new Author(authorName, authorEmail);
        author.addCommits(commits);
        authors.add(author);
    }

    private static AveragesData findByAuthorName(List<AveragesData> dataList, String authorName) {
        return dataList.stream()
                .filter(c -> c.getAuthorName().equals(authorName))
                .findFirst()
                .orElse(null);
    }

    @Test
    public void addDeleteCalculatorTest() throws Exception {

        AveragesCalculator averagesCalculator = new AveragesCalculator(authors);
        List<AveragesData> dataList = averagesCalculator.generateData();

        assertEquals(expectedAdded, (double)findByAuthorName(dataList, authorName).getAvgAddedLines(), DELTA);
        assertEquals(expectedDeleted, (double)findByAuthorName(dataList, authorName).getAvgDeletedLines(), DELTA);
        assertEquals(expectedChanged, (double)findByAuthorName(dataList, authorName).getAvgChangedLines(), DELTA);
    }

}
