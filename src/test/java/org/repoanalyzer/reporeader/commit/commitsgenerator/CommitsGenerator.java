package org.repoanalyzer.reporeader.commit.commitsgenerator;

import org.repoanalyzer.reporeader.commit.Author;
import org.repoanalyzer.reporeader.commit.AuthorProvider;
import org.repoanalyzer.reporeader.commit.Commit;

import java.util.*;

public class CommitsGenerator implements ICommitsGenerator {
    private AuthorProvider authorProvider;
    private Map<TestCommitBuilder, Integer>  testCommitBuilderToCommitsNumber;

    public CommitsGenerator() {
        this.testCommitBuilderToCommitsNumber = new HashMap<>();
        this.authorProvider = new AuthorProvider();
    }

    public TestCommitBuilder createNewTestCommit() {
        return createNewTestCommit(1);
    }

    public TestCommitBuilder createNewTestCommit(int commitsNumber) {
        TestCommitBuilder testCommitBuilder = new TestCommitBuilder(authorProvider);
        testCommitBuilderToCommitsNumber.put(testCommitBuilder, commitsNumber);
        return testCommitBuilder;
    }

    @Override
    public List<Commit> getCommits() {
        List<Commit> result = new ArrayList<>();
        for(Map.Entry<TestCommitBuilder, Integer> entry: testCommitBuilderToCommitsNumber.entrySet()) {
            for(int i = 0; i < entry.getValue(); i++) {
                result.add(entry.getKey().createCommit());
            }
        }
        return result;
    }

    public List<Commit> getCommits(Author author) {
        List<Commit> result = new ArrayList<>();
        for(Map.Entry<TestCommitBuilder, Integer> entry: testCommitBuilderToCommitsNumber.entrySet()) {
            for(int i = 0; i < entry.getValue(); i++) {
                if(entry.getKey().createCommit().getAuthor().getFirstName().equals(author.getFirstName()))
                    result.add(entry.getKey().createCommit());
            }
        }
        return result;
    }

}