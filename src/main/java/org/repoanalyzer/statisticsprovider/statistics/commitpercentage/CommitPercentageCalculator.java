package org.repoanalyzer.statisticsprovider.statistics.commitpercentage;


import org.repoanalyzer.reporeader.commit.Author;
import org.repoanalyzer.reporeader.commit.Commit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommitPercentageCalculator {
    private Set<Author> authors;

    public CommitPercentageCalculator(Set<Author> authors) {
        this.authors = authors;
    }

    public List<CommitPercentageData> generateData(){
        List<CommitPercentageData> data = new ArrayList<>();
        for(Author author: authors){
            data.add(new CommitPercentageData(author.getFirstName(),author.getCommits().size()));
        }
        return data;
    }
}
