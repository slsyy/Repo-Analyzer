package org.repoanalyzer.statisticsprovider.statistics.averages;

import org.repoanalyzer.reporeader.commit.Author;
import org.repoanalyzer.reporeader.commit.Commit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Jakub on 2017-01-14.
 */
public class AveragesCalculator {
    private Set<Author> authors;

    public AveragesCalculator(Set<Author> authors) {
        this.authors = authors;
    }

    public List<AveragesData> generateData(){
        List<AveragesData> result = new ArrayList<>();

        for(Author author : authors){
            Integer sumAddedLines = 0;
            Integer sumDeletedLines = 0;
            Integer sumChangedLines = 0;

            for(Commit commit : author.getCommits()){
                sumAddedLines += commit.getAddedLinesNumber();
                sumDeletedLines += commit.getDeletedLinesNumber();
                sumChangedLines += commit.getChangedLinesNumber();
            }

            AveragesData data = new AveragesData(author.getFirstName());
            data.setAvgAddedLines(1.0f*(sumAddedLines/author.getCommits().size()));
            data.setAvgDeletedLines(1.0f*(sumDeletedLines/author.getCommits().size()));
            data.setAvgChangedLines(1.0f*(sumChangedLines/author.getCommits().size()));

            result.add(data);
        }

        return result;
    }
}
