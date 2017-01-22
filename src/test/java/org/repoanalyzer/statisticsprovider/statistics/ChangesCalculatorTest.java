package org.repoanalyzer.statisticsprovider.statistics;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.repoanalyzer.reporeader.author.Author;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.statistics.changes.ChangesCalculator;
import org.repoanalyzer.statisticsprovider.statistics.changes.ChangesData;
import org.repoanalyzer.statisticsprovider.statistics.commitsgenerator.CommitsGenerator;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)

public class ChangesCalculatorTest {

    private String authorName;
    private String authorEmail;
    private int expectedChangesLines;
    private int[] addedAndDeleted;
    private Set<Author> authors;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "first", "first@first", 124, new int[]{25, 2, 42, 12, 33, 10}},
                { "second", "second@second", 110, new int[]{10, 2, 30, 50, 0, 18}},
                { "third", "third@third", 1929, new int[]{507, 204, 930, 247, 21, 20}}
        });
    }

    public ChangesCalculatorTest(String authorName, String authorEmail, int expectedChangesLines, int[] args){
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.expectedChangesLines = expectedChangesLines;
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

    private static ChangesData findByAuthorName(List<ChangesData> dataList, String authorName) {
        return dataList.stream()
                .filter(c -> c.getAuthorName().equals(authorName))
                .findFirst()
                .orElse(null);
    }

    @Test
    public void changesCalculatorTest() throws Exception {

        ChangesCalculator changesCalculator = new ChangesCalculator(authors);
        List<ChangesData> dataList = changesCalculator.generateData();

        assertEquals(expectedChangesLines, (int)findByAuthorName(dataList, authorName).getChanges());
    }

}
