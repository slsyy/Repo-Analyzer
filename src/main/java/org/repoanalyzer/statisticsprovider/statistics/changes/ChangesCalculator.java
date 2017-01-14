package org.repoanalyzer.statisticsprovider.statistics.changes;

import org.repoanalyzer.reporeader.commit.Author;
import org.repoanalyzer.reporeader.commit.Commit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Jakub on 2017-01-14.
 */
public class ChangesCalculator {

    private Set<Author> authors;

    public ChangesCalculator(Set<Author> authors) {
        this.authors = authors;
    }

    public List<ChangesData> generateData(){
        List<ChangesData> result = new ArrayList<>();
        for(Author author : authors){
            ChangesData data = new ChangesData(author.getFirstName());
            for(Commit commit : author.getCommits()){
                data.incrementChanges(commit.getAddedLinesNumber()+ commit.getDeletedLinesNumber());
            }
            result.add(data);
        }

        return result;
    }
}
