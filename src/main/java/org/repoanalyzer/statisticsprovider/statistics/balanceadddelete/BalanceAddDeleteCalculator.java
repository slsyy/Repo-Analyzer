package org.repoanalyzer.statisticsprovider.statistics.balanceadddelete;

import org.repoanalyzer.reporeader.commit.Author;
import org.repoanalyzer.reporeader.commit.Commit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Jakub on 2016-12-18.
 */
public class BalanceAddDeleteCalculator {
    private Set<Author> authors;

    public BalanceAddDeleteCalculator(Set<Author> authors) {
        this.authors = authors;
    }

    public List<BalanceAddDeleteData> generateData(){
        List<BalanceAddDeleteData> result = new ArrayList<>();
        for(Author author : authors){
            BalanceAddDeleteData data = new BalanceAddDeleteData(author);
            for(Commit commit : author.getCommits()){
                data.addAddedLines(commit.getAddedLinesNumber());
                data.addDeletedLines(commit.getDeletedLinesNumber());
            }
            result.add(data);
        }
        return result;
    }
}
