package org.repoanalyzer.statisticsprovider.component.balanceadddelete;

import org.repoanalyzer.reporeader.commit.Author;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.statisticsprovider.component.balanceadddelete.BalanceAddDeleteData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakub on 2016-12-18.
 */
public class BalanceAddDeleteCalculator {
    private List<Commit> commits;

    public BalanceAddDeleteCalculator(List<Commit> commits) {
        this.commits = commits;
    }

    public List<BalanceAddDeleteData> generateData(){
        List<BalanceAddDeleteData> result = new ArrayList<>();
        for(Commit commit : commits){
            Author author = commit.getAuthor();
            Boolean newData = true;
            for(BalanceAddDeleteData data : result){
                if(data.getAuthor().equals(author)){
                    newData = false;
                    data.addAddedLines(commit.getAddedLinesNumber());
                    data.addDeletedLines(commit.getDeletedLinesNumber());
                    break;
                }
            }
            if(newData){
                BalanceAddDeleteData data = new BalanceAddDeleteData(author);
                data.addDeletedLines(commit.getDeletedLinesNumber());
                data.addAddedLines(commit.getAddedLinesNumber());
                result.add(data);
            }
        }
        return result;
    }
}
