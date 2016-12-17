package org.repoanalyzer.statisticsprovider;


import com.github.javafaker.Faker;
import org.joda.time.DateTime;
import org.repoanalyzer.reporeader.AbstractRepoReader;
import org.repoanalyzer.reporeader.Progress;
import org.repoanalyzer.reporeader.commit.Author;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.reporeader.commit.CommitBuilder;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class MockedRepoReader extends AbstractRepoReader {

    private List<Commit> commits;

    static private Object getRandomObject(Collection from) {
        Random rnd = new Random();
        int i = rnd.nextInt(from.size());
        return from.toArray()[i];
    }

    public MockedRepoReader(String url) {
        super(url);
        commits = new ArrayList<Commit>();


        Faker faker = new Faker();
        List<Author> authors = new ArrayList<Author>();
        for(int i = 0; i < 10; ++i){
            String name = faker.name().fullName();
            String email = name + "@a.b";
            authors.add(new Author(faker.name().fullName(), email));

        }

        for(int i = 0; i < 200; ++i) {
            final Commit commit = new CommitBuilder().
                    setHashCode("sfnsofsf").
                    setAuthor((Author)getRandomObject(authors)).
                    setDate(DateTime.now()).
                    setMessage(faker.chuckNorris().fact()).
                    setDeletedLinesNumber(5).
                    setAddedLinesNumber(100).
                    setChangedLinesNumber(5).
                    createCommit();

            commits.add(commit);
        }
    }

    public Future<List<Commit>> getCommits() {
        Callable<List<Commit>> task = () -> {
            return commits;
        };

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<List<Commit>> future = executor.submit(task);
        executor.shutdown();

        return future;
    }

    public Progress getProgress() {
        return null;
    }
}
