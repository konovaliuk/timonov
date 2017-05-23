package ua.timonov.web.project.service;

import ua.timonov.web.project.dao.DaoFactory;
import ua.timonov.web.project.dao.DatabaseType;
import ua.timonov.web.project.dao.daointerface.LocationDao;
import ua.timonov.web.project.exception.ServiceLayerException;
import ua.timonov.web.project.model.location.Location;

import java.util.List;

public class LocationService {

    private static final LocationService instance = new LocationService();

    private DaoFactory daoFactory = DaoFactory.getFactory(DatabaseType.MYSQL);
    private LocationDao locationDao = daoFactory.createLocationDao();

    private LocationService() {
    }

    public static LocationService getInstance() {
        return instance;
    }

    public List<Location> getAll() {
        return locationDao.findAll();
    }

    public Location findById(long id) throws ServiceLayerException {
        Location location = locationDao.findById(id);
        if (location == null) {
            throw new ServiceLayerException("ID " + id + "does not exist in" + locationDao.getName());
        }
        return location;
    }
}
