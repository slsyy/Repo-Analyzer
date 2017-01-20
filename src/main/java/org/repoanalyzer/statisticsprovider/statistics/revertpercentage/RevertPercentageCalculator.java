package org.repoanalyzer.statisticsprovider.statistics.revertpercentage;


import org.repoanalyzer.reporeader.author.Author;
import org.repoanalyzer.reporeader.commit.Commit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RevertPercentageCalculator {
    private Set<Author> authors;

    public RevertPercentageCalculator(Set<Author> authors){
            this.authors = authors;
    }

    public List<RevertPercentageData> generateStatistics(){
        List<RevertPercentageData> result = new ArrayList<>();
        for(Author author : authors){
            RevertPercentageData data = new RevertPercentageData(author);
            int reverts = 0;
            for(Commit c : author.getCommits()){
                String[] words = c.getMessage().split(" ");
                if(words[0].equals("Revert"))
                    reverts++;
            }
            data.setPercentage((reverts * 100.0f/ author.getCommits().size()));
            result.add(data);
        }
        return result;
    }
}
