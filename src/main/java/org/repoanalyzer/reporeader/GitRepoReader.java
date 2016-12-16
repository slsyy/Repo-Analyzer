package org.repoanalyzer.reporeader;

import org.repoanalyzer.reporeader.commit.Commit;
import java.util.List;

public class GitRepoReader extends AbstractRepoReader{

    public GitRepoReader(String url){
        super(url);
    }

    public List<Commit> getCommits() {
        return null;
    }

    public Progress getProgress() {
        return null;
    }
}
