package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.Dao;
import ua.timonov.web.project.dao.DaoFactory;
import ua.timonov.web.project.dao.DatabaseType;
import ua.timonov.web.project.dao.Entity;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.util.ExceptionMessages;

import java.util.List;

public abstract class DataService<T extends Entity, F extends Entity> {

    private static final Logger LOGGER = Logger.getLogger(DataService.class);
    protected static DaoFactory daoFactory = DaoFactory.getFactory(DatabaseType.MYSQL);

    protected Dao<T> dao;
    protected Dao<F> daoForeignKey;

    public DataService(Dao<T> dao) {
        this.dao = dao;
    }

    public DataService(Dao<T> dao, Dao<F> daoForeignKey) {
        this.dao = dao;
        this.daoForeignKey = daoForeignKey;
    }

    public void save(T entity) throws ServiceException {
        if (!dao.save(entity)) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.SAVE_FAILED) + " " + dao.getName();
            LOGGER.error(message);
            throw new ServiceException(message);
        }
    }

    public void delete(long id) throws ServiceException {
        if (daoForeignKey != null) {
            checkEntityWithForeignKey(id);
        }
        if (!dao.delete(id)) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.DELETE_FAILED) + " " + dao.getName();
            LOGGER.error(message);
            throw new ServiceException(message);
        }
    }

    private void checkEntityWithForeignKey(long id) {
        F entity = daoForeignKey.findByForeignId(id, dao.getName());
        if (entity != null) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.IMPOSSIBLE_TO_DELETE) + " " +
                    dao.getName() + ExceptionMessages.getMessage(ExceptionMessages.ANOTHER_HOLDS) + " " +
                    daoForeignKey.getName();
            LOGGER.warn(message);
            throw new ServiceException(message);
        }
    }

    public T findById(long id) throws ServiceException {
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

    public List<T> findAll() {
        List<T> itemList = dao.findAll();
        if (itemList == null) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.FIND_ITEMS_FAILED) + " " +
                    daoForeignKey.getName();
            LOGGER.error(message);
            throw new ServiceException(message);
        }
        return itemList;
    }
}
