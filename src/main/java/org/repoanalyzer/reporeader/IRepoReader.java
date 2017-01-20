package org.repoanalyzer.reporeader;

import org.repoanalyzer.reporeader.author.Author;
import org.repoanalyzer.reporeader.author.AuthorProvider;
import org.repoanalyzer.reporeader.commit.Commit;
import org.repoanalyzer.reporeader.exceptions.CannotOpenAuthorFileException;
import org.repoanalyzer.reporeader.exceptions.InvalidJsonDataFormatException;
import org.repoanalyzer.reporeader.exceptions.JsonParsingException;
import org.repoanalyzer.reporeader.exceptions.RepositoryNotFoundOrInvalidException;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

public interface IRepoReader {
    Future<List<Commit>> getCommits() throws RepositoryNotFoundOrInvalidException, JsonParsingException, CannotOpenAuthorFileException, InvalidJsonDataFormatException;
    Set<Author> getAuthors();
    Progress getProgress();
}
