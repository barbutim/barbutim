package cz.cvut.fel.nss.careerpages.dao;

import cz.cvut.fel.nss.careerpages.model.JobJournal;
import org.springframework.stereotype.Repository;

/**
 * JobJournal dao
 */
@Repository
public class JobJournalDao extends BaseDao<JobJournal> {
    /**
     * constructor
     */
    protected JobJournalDao() {
        super(JobJournal.class);
    }
}
