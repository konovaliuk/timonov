package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.Dao;
import ua.timonov.web.project.dao.DaoFactory;
import ua.timonov.web.project.dao.DatabaseType;
import ua.timonov.web.project.dao.Entity;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.util.ExceptionMessages;

import java.util.List;

/**
 * Represents abstract Service for interact with DAO layer interfaces
 * @param <T>       own entity type
 * @param <F>       entity type with foreign key to own type
 */
public abstract class DataService<T extends Entity, F extends Entity> {

    private static final Logger LOGGER = Logger.getLogger(DataService.class);
    protected static DaoFactory daoFactory = DaoFactory.getFactory(DatabaseType.MYSQL);

    /* own DAO interface */
    protected Dao<T> dao;

    /* DAO interface with foreign key to own DAO */
    protected Dao<F> daoForeignKey;

    public DataService(Dao<T> dao) {
        this.dao = dao;
    }

    public DataService(Dao<T> dao, Dao<F> daoForeignKey) {
        this.dao = dao;
        this.daoForeignKey = daoForeignKey;
    }

    /**
     * saves entity to database
     * @param entity                    persisted entity
     * @throws ServiceException         if operation is not successful
     */
    public void save(T entity) throws ServiceException {
        if (!dao.save(entity)) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.SAVE_FAILED) + " " + dao.getName();
            LOGGER.error(message);
            throw new ServiceException(message);
        }
    }

    /**
     * deletes entity from database
     * @param id                        persisted entity
     * @throws ServiceException         if operation is not successful
     */
    public void delete(Long id) throws ServiceException {
        if (daoForeignKey != null) {
            checkEntityWithForeignKey(id);
        }
        if (!dao.delete(id)) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.DELETE_FAILED) + " " + dao.getName();
            LOGGER.error(message);
            throw new ServiceException(message);
        }
    }

    /**
     * finds entity in database by its ID
     * @param id        entity ID
     * @return          found entity or null if not not found
     */
    public T findById(Long id) throws ServiceException {
        T value = dao.findById(id);
        if (value == null) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.FIND_ITEM_FAILED) + " " + id +
                    ExceptionMessages.getMessage(ExceptionMessages.FIND_IN_TABLE) + " " +
                    daoForeignKey.getName();
            LOGGER.error(message);
            throw new ServiceException(message);
        }
        return value;
    }

    /**
     * finds all entities in database
     * @return          List of entities
     */
    public List<T> findAll() throws ServiceException {
        List<T> itemList = dao.findAll();
        if (itemList == null) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.FIND_ITEMS_FAILED) + " " +
                    daoForeignKey.getName();
            LOGGER.error(message);
            throw new ServiceException(message);
        }
        return itemList;
    }

    /* checks if other entity has foreign key on this, so own entity can not be deleted */
    private void checkEntityWithForeignKey(long id) throws ServiceException {
        F entity = daoForeignKey.findByForeignId(id, dao.getName());
        if (entity != null) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.IMPOSSIBLE_TO_DELETE) + " " +
                    dao.getName() + ExceptionMessages.getMessage(ExceptionMessages.ANOTHER_HOLDS) + " " +
                    daoForeignKey.getName();
            LOGGER.warn(message);
            throw new ServiceException(message);
        }
    }
}
