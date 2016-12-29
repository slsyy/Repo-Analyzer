package org.repoanalyzer.statisticsprovider.statistics.revertpercentage;


import org.repoanalyzer.reporeader.commit.Author;
import org.repoanalyzer.reporeader.commit.Commit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RevertPercentageCalculator {
    private List<Commit> commits;

    public RevertPercentageCalculator(List<Commit> commits){
        this.commits = commits;
    }

    public List<RevertPercentageData> generateStatistics(){
        List<RevertPercentageData> result = new ArrayList<>();
        Set<String> authors = new HashSet<>();
        for(Commit commit : commits){
            Author author =commit.getAuthor();
            if(!authors.contains(author.getFirstName())){
                RevertPercentageData data =new RevertPercentageData(author);
                Integer reverts = 0;
                for(Commit c : author.getCommits()){
                    String[] words = c.getMessage().split(" ");
                    if(words[0].equals("Revert"))
                        reverts++;
                }
                data.setPercentage((reverts * 100.0f/ author.getCommits().size()));
                result.add(data);
                authors.add(author.getFirstName());
            }
        }

        return result;
    }
}
