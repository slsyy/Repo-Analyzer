package org.repoanalyzer.statisticsprovider.data;


import org.repoanalyzer.reporeader.commit.Author;
import org.repoanalyzer.reporeader.commit.Commit;

import java.util.List;
import java.util.Map;

public class RevertStatisticsData {
    public List<Commit> commits;
    public Map<Author, Float> authorToRevertsPersentage;

    public RevertStatisticsData(List<Commit> commits){
        this.commits = commits;
    }

    public Map<Author, Float> getData(){
        return authorToRevertsPersentage;
    }
}
