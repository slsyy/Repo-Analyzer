package org.repoanalyzer.statisticsprovider.statistics;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.statistics.commitsgenerator.CommitsGenerator;
import org.repoanalyzer.statisticsprovider.statistics.commitsperhour.CommitsPerHourCalculator;
import org.repoanalyzer.statisticsprovider.statistics.commitsperhour.CommitsPerHourData;
import org.repoanalyzer.statisticsprovider.statistics.commitsperhour.Days;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class CommitsPerHourCalculatorTest {

    private static final double DELTA = 0.01;
    private int firstDateCommits;
    private int secondDateCommits;
    private int thirdDateCommits;
    private DateTime firstDate;
    private DateTime secondDate;
    private DateTime thirdDate;
    private List<Commit> commits;

    @Before
    public void setUp() throws Exception {
        this.firstDateCommits = 24;
        this.secondDateCommits = 36;
        this.thirdDateCommits = 120;
        // year, month, day, hour, minut
        this.firstDate = new DateTime(2016, 12, 27, 20, 27);
        this.secondDate = new DateTime(2016, 11, 22, 20, 43);
        this.thirdDate = new DateTime(2017, 1, 10, 14, 27);

        CommitsGenerator commitsGenerator = new CommitsGenerator();

        commitsGenerator.createNewTestCommit(firstDateCommits)
                .setAuthorName("first")
                .setAuthorEmail("first@first")
                .setDateTime(firstDate);
        commitsGenerator.createNewTestCommit(secondDateCommits)
                .setAuthorName("second")
                .setAuthorEmail("second@second")
                .setDateTime(secondDate);
        commitsGenerator.createNewTestCommit(thirdDateCommits)
                .setAuthorName("third")
                .setAuthorEmail("third@third")
                .setDateTime(thirdDate);

        this.commits = commitsGenerator.getCommits();
    }

    private int getMax(int first, int second, int third){
        return Math.max(Math.max(first, second), third);
    }

    @Test
    public void commitsPerHourCalculatorTest() throws Exception {

        CommitsPerHourCalculator commitsPerHourCalculator = new CommitsPerHourCalculator(commits);
        CommitsPerHourData commitsPerHourData = commitsPerHourCalculator.generateData();

        assertEquals(getMax(firstDateCommits, secondDateCommits, thirdDateCommits),
                commitsPerHourData.getMaxCommitsPerDay());

        assertEquals(firstDateCommits + secondDateCommits, commitsPerHourData.getDataPerDayAndHour(
                Days.fromDateTime(firstDate.getDayOfWeek()), firstDate.getHourOfDay()));
        assertEquals(thirdDateCommits, commitsPerHourData.getDataPerDayAndHour(
                Days.fromDateTime(thirdDate.getDayOfWeek()), thirdDate.getHourOfDay()));
    }

}
