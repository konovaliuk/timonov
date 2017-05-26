package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.Dao;
import ua.timonov.web.project.dao.DaoFactory;
import ua.timonov.web.project.dao.DatabaseType;
import ua.timonov.web.project.dao.Entity;
import ua.timonov.web.project.exception.ServiceException;

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
            LOGGER.error("Saving " + dao.getName() + " failed!");
            throw new ServiceException("Saving " + dao.getName() + " failed!");
        }
    }

    public void delete(long id) throws ServiceException {
        if (daoForeignKey != null) {
            findEntityWithForeignKey(id);
        }
        if (!dao.delete(id)) {
            LOGGER.error("Deleting " + dao.getName() + " failed!");
            throw new ServiceException("Deleting " + dao.getName() + " failed!");
        }
    }

    private void findEntityWithForeignKey(long id) {
        F entity = daoForeignKey.findByForeignId(id, dao.getName());
        if (entity != null) {
            LOGGER.warn("Deleting chosen " + dao.getName() + " is impossible! There is a(n) " +
                    daoForeignKey.getName() + " that holds it.");
            throw new ServiceException("Deleting chosen " + dao.getName() + " is impossible! There is a(n) " +
                    daoForeignKey.getName() + " that holds it.");
        }
    }

    public T findById(long id) throws ServiceException {
        T value = dao.findById(id);
        if (value == null) {
            LOGGER.error("Item with id = " + id + " not founded in " + dao.getName());
            throw new ServiceException("Item with id = " + id + " not founded in " + dao.getName());
        }
        return value;
    }

    public List<T> findAll() {
        List<T> itemList = dao.findAll();
        if (itemList == null) {
            LOGGER.error("Items not founded in " + dao.getName());
            throw new ServiceException("Items not founded in " + dao.getName());
        }
        return itemList;
    }
}
