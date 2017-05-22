package ua.timonov.web.project.service;

import ua.timonov.web.project.dao.daointerface.LocationDao;
import ua.timonov.web.project.dao.jdbc.mysql.MysqlDaoFactory;
import ua.timonov.web.project.model.location.Location;

import java.util.List;

public class LocationService {

    LocationDao locationDao = MysqlDaoFactory.getInstance().createLocationDao();

    public List<Location> getAll() {
        return locationDao.findAll();
    }

    public Location findById(long id) throws DataServiceException {
        Location location = locationDao.findById(id);
        if (location == null) {
            throw new DataServiceException("ID " + id + "does not exist in" + locationDao.getName());
        }
        return location;
    }
}
