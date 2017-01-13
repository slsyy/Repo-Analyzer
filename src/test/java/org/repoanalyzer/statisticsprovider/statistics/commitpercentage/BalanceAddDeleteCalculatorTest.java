package org.repoanalyzer.statisticsprovider.statistics.commitpercentage;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.reporeader.commit.commitsgenerator.CommitsGenerator;
import org.repoanalyzer.statisticsprovider.statistics.balanceadddelete.BalanceAddDeleteCalculator;
import org.repoanalyzer.statisticsprovider.statistics.balanceadddelete.BalanceAddDeleteData;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BalanceAddDeleteCalculatorTest {

//    private CommitsGenerator commitsGenerator;
//    private String authorName;
//    private String authorEmail;
//    private Integer exptectedAdded;
//    private Integer exptectedDeleted;
//    private int[] addedAndDeleted;
//
//    @Parameterized.Parameters
//    public static Collection<Object[]> data() {
//        return Arrays.asList(new Object[][] {
//                { "first", "first@first", 100,  24, new int[]{25, 2, 42, 12, 33, 10}},
//                { "second", "second@second", 40, 70, new int[]{10, 2, 30, 50, 0, 18}},
//                { "third", "third@third", 1458, 471, new int[]{507, 204, 930, 247, 21, 20}}
//        });
//    }
//
//    public BalanceAddDeleteCalculatorTest(String authorName, String authorEmail, int exptectedAdded, int exptectedDeleted,
//                                          int[] args){
//        this.authorName = authorName;
//        this.authorEmail = authorEmail;
//        this.exptectedAdded = exptectedAdded;
//        this.exptectedDeleted = exptectedDeleted;
//        this.addedAndDeleted = args;
//    }
//
//    @Before
//    public void setUp() throws Exception {
//        commitsGenerator = new CommitsGenerator();
//    }
//
//    private static BalanceAddDeleteData findByAuthorName(List<BalanceAddDeleteData> dataList, String authorName) {
//        return dataList.stream()
//                .filter(c -> c.getAuthor().getFirstName().equals(authorName))
//                .findFirst()
//                .orElse(null);
//    }
//
//    @Test
//    public void generateData() throws Exception {
//        for(int i = 0; i < addedAndDeleted.length; i += 2){
//            commitsGenerator.createNewTestCommit()
//                    .setAuthorName(authorName)
//                    .setAuthorEmail(authorEmail)
//                    .setAddedLinesNumber(addedAndDeleted[i])
//                    .setDeletedLinesNumber(addedAndDeleted[i+1]);
//        }
//
//
//        List<Commit> commits = commitsGenerator.getCommits();
//        BalanceAddDeleteCalculator balanceAddDeleteCalculator = new BalanceAddDeleteCalculator(commits);
//        List<BalanceAddDeleteData> dataList = balanceAddDeleteCalculator.generateData();
//
//        assertEquals(exptectedAdded, findByAuthorName(dataList, authorName).getAddedLines());
//        assertEquals(exptectedDeleted, findByAuthorName(dataList, authorName).getDeletedLines());
//    }

}
