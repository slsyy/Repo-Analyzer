package org.repoanalyzer.statisticsprovider.statistics.balanceadddelete;

import org.repoanalyzer.reporeader.commit.Author;

/**
 * Created by Jakub on 2016-12-18.
 */
public class BalanceAddDeleteData {
    private Author author;
    private Integer addedLines;
    private Integer deletedLines;

    public BalanceAddDeleteData(Author author) {
        this.author = author;
        this.addedLines = 0;
        this.deletedLines = 0;
    }

    public Author getAuthor() {
        return author;
    }

    public Integer getAddedLines() {
        return addedLines;
    }

    public Integer getDeletedLines() {
        return deletedLines;
    }

    public void addAddedLines(Integer i){
        addedLines += i;
    }

    public void addDeletedLines(Integer i){
        deletedLines += i;
    }
}
