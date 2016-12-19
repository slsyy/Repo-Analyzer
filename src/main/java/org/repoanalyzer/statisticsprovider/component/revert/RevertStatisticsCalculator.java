package org.repoanalyzer.statisticsprovider.component.revert;


import org.repoanalyzer.reporeader.commit.Author;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.data.RevertStatisticsData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RevertStatisticsCalculator {
    private List<Commit> commits;

    public RevertStatisticsCalculator(List<Commit> commits){
        this.commits = commits;
    }

    public List<RevertStatisticsData> generateStatistics(){
        List<RevertStatisticsData> result = new ArrayList<>();
        Set<String> authors = new HashSet<>();
        for(Commit commit : commits){
            Author author =commit.getAuthor();
            if(!authors.contains(author.getFirstName())){
                RevertStatisticsData data =new RevertStatisticsData(author);
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
