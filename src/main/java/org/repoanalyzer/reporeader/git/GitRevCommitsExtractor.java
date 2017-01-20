package org.repoanalyzer.reporeader.git;

import org.repoanalyzer.reporeader.exceptions.RepositoryNotFoundOrInvalidException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.filter.RevFilter;

import java.util.LinkedList;
import java.util.List;

public class GitRevCommitsExtractor {
    private final Git repo;

    public GitRevCommitsExtractor(Git repo) {
        this.repo = repo;
    }

    public List<RevCommit> getListOfRevCommitsFromRepository() throws RepositoryNotFoundOrInvalidException {
        Iterable<RevCommit> iterableCommits;
        try {
            iterableCommits = this.repo.log().setRevFilter(RevFilter.NO_MERGES).call();
        } catch (GitAPIException exception) {
            throw new RepositoryNotFoundOrInvalidException();
        }

        List<RevCommit> commits = new LinkedList<>();
        for (RevCommit commit : iterableCommits)
            commits.add(commit);

        return commits;
    }
}
