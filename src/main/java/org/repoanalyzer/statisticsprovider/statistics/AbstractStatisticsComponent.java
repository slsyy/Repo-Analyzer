package org.repoanalyzer.statisticsprovider.statistics;

import org.repoanalyzer.reporeader.commit.Author;
import org.repoanalyzer.reporeader.commit.Commit;

import java.util.List;
import java.util.Set;

public abstract class AbstractStatisticsComponent implements IStatisticsComponent{
    protected List<Commit> commits;
    protected Set<Author> authors;

    public AbstractStatisticsComponent(List<Commit> commits){
        this.commits = commits;
    }

    public AbstractStatisticsComponent(Set<Author> authors) {
        this.authors = authors;
    }
}
