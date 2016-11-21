package org.repoanalyzer.statisticsprovider;

import org.repoanalyzer.reporeader.IRepoReader;
import org.repoanalyzer.reporeader.commit.Commit;

import  java.lang.System;

public class App {
    public static void main(String args[]){
        IRepoReader repoReader = new MockedRepoReader("bla");
        for(Commit commit: repoReader.getCommits()){
            System.out.println(commit);
        }
    }
}
