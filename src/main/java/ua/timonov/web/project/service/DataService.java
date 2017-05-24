package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.Dao;
import ua.timonov.web.project.dao.DaoFactory;
import ua.timonov.web.project.dao.DatabaseType;
import ua.timonov.web.project.dao.Entity;
import ua.timonov.web.project.exception.ServiceException;

import java.util.List;

public abstract class DataService<T extends Entity> {

    private static final Logger LOGGER = Logger.getLogger(DataService.class);
    protected static DaoFactory daoFactory = DaoFactory.getFactory(DatabaseType.MYSQL);

    private String serviceName;
    protected Dao<T> dao;

    public DataService(Dao<T> dao, String serviceName) {
        this.dao = dao;
        this.serviceName = serviceName;
    }

    public void save(T entity, long... externalId) throws ServiceException {
        if (!dao.save(entity, externalId)) {
            LOGGER.error("Saving " + serviceName + " failed!");
            throw new ServiceException("Saving " + serviceName + " failed!");
        }
    }

    public void delete(long id) throws ServiceException {
        if (!dao.delete(id)) {
            LOGGER.error("Deleting " + serviceName + " failed!");
            throw new ServiceException("Deleting " + serviceName + " failed!");
        }
    }

    public T findById(long id) throws ServiceException {
        T value = dao.findById(id);
        if (value == null) {
            LOGGER.error("Item with id = " + id + " not founded in " + serviceName);
            throw new ServiceException("Item with id = " + id + " not founded in " + serviceName);
        }
        return value;
    }

    public List<T> findAll() {
        List<T> itemList = dao.findAll();
        if (itemList == null) {
            LOGGER.error("Items not founded in " + serviceName);
            throw new ServiceException("Items not founded in " + serviceName);
        }
        return itemList;
    }
}
