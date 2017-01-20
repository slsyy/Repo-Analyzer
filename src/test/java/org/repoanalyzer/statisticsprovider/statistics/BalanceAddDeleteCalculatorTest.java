package org.repoanalyzer.statisticsprovider.statistics;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.repoanalyzer.reporeader.author.Author;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.statistics.commitsgenerator.CommitsGenerator;
import org.repoanalyzer.statisticsprovider.statistics.balanceadddelete.BalanceAddDeleteCalculator;
import org.repoanalyzer.statisticsprovider.statistics.balanceadddelete.BalanceAddDeleteData;

import java.util.*;

import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)

public class BalanceAddDeleteCalculatorTest {

    private String authorName;
    private String authorEmail;
    private Integer expectedAdded;
    private Integer expectedDeleted;
    private int[] addedAndDeleted;
    private Set<Author> authors;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "first", "first@first", 100,  24, new int[]{25, 2, 42, 12, 33, 10}},
                { "second", "second@second", 40, 70, new int[]{10, 2, 30, 50, 0, 18}},
                { "third", "third@third", 1458, 471, new int[]{507, 204, 930, 247, 21, 20}}
        });
    }

    public BalanceAddDeleteCalculatorTest(String authorName, String authorEmail, int expectedAdded, int expectedDeleted,
                                          int[] args){
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.expectedAdded = expectedAdded;
        this.expectedDeleted = expectedDeleted;
        this.addedAndDeleted = args;
    }

    @Before
    public void setUp() throws Exception {
        CommitsGenerator commitsGenerator = new CommitsGenerator();

        for(int i = 0; i < addedAndDeleted.length; i += 2){
            commitsGenerator.createNewTestCommit()
                    .setAuthorName(authorName)
                    .setAuthorEmail(authorEmail)
                    .setAddedLinesNumber(addedAndDeleted[i])
                    .setDeletedLinesNumber(addedAndDeleted[i+1]);
        }

        List<Commit> commits = commitsGenerator.getCommits();
        authors = new HashSet<>();
        Author author = new Author(authorName, authorEmail);
        author.addCommits(commits);
        authors.add(author);
    }

    private static BalanceAddDeleteData findByAuthorName(List<BalanceAddDeleteData> dataList, String authorName) {
        return dataList.stream()
                .filter(c -> c.getAuthor().getFirstName().equals(authorName))
                .findFirst()
                .orElse(null);
    }

    @Test
    public void generateDataAddDeleteCalculator() throws Exception {

        BalanceAddDeleteCalculator balanceAddDeleteCalculator = new BalanceAddDeleteCalculator(authors);
        List<BalanceAddDeleteData> dataList = balanceAddDeleteCalculator.generateData();

        assertEquals(expectedAdded, findByAuthorName(dataList, authorName).getAddedLines());
        assertEquals(expectedDeleted, findByAuthorName(dataList, authorName).getDeletedLines());
    }


}
