package ua.timonov.web.project.dao;

import java.io.Serializable;

/**
 * Persisted entity
 */
public interface Entity extends Serializable {

    /**
     * returns entity ID
     * @return      entity ID
     */
    long getId();

    /**
     * sets entity ID
     * @param id    given ID
     */
    void setId(long id);
}
