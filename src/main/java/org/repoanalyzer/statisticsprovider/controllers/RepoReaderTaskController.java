package org.repoanalyzer.statisticsprovider.controllers;

import org.repoanalyzer.reporeader.IRepoReader;
import org.repoanalyzer.reporeader.RepoReaderFactory;
import org.repoanalyzer.reporeader.author.AuthorProvider;
import org.repoanalyzer.reporeader.author.FilePreloadedAuthorProvider;
import org.repoanalyzer.reporeader.exceptions.*;
import org.repoanalyzer.statisticsprovider.view.RepoReaderProgressBarView;



public class RepoReaderTaskController {
    private IRepoReader repoReader;
    private StatisticComponentsCreator statisticComponentsCreator;

    public RepoReaderTaskController(StatisticComponentsCreator statisticComponentsCreator) {
        this.statisticComponentsCreator = statisticComponentsCreator;
    }

    public void runRepoReaderTask(String url, String authorFile)
            throws RepositoryNotFoundOrInvalidException, AuthorProviderMustExistsException {
        RepoReaderFactory repoReaderFactory = new RepoReaderFactory();
        repoReader = repoReaderFactory.create(url, prepareAuthorProvider(authorFile));


        RepoReaderTask task = new RepoReaderTask(repoReader);
        RepoReaderProgressBarView repoReaderProgressBarView = new RepoReaderProgressBarView();
        repoReaderProgressBarView.activateProgressBar(task);


        task.setOnSucceeded(workerStateEvent -> {
            repoReaderProgressBarView.getDialogStage().close();
            statisticComponentsCreator.createStatisticsComponents(task, repoReader.getAuthors());
        });

        task.setOnFailed(workerStateEvent -> {
            task.handleException(repoReaderProgressBarView);
        });

        repoReaderProgressBarView.getDialogStage().show();
        new Thread(task).start();
    }

    private AuthorProvider prepareAuthorProvider(String authorFile) {
        if (authorFile.isEmpty()) return  new AuthorProvider();

        try {
            return new FilePreloadedAuthorProvider(authorFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Ignoring provided file with authors.");
            return new AuthorProvider();
        }
    }
}
