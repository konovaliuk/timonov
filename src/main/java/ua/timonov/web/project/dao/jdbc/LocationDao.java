package ua.timonov.web.project.dao.jdbc;

import org.apache.log4j.Logger;
import ua.timonov.web.project.model.location.Country;
import ua.timonov.web.project.model.location.Location;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationDao extends EntityDao {
    private static final Logger LOGGER = Logger.getLogger(LocationDao.class);

    private CountryDao countryDao = new CountryDao();

    public Location getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("location_id");
        String location = resultSet.getString("location");
        Country country = countryDao.getEntityFromResultSet(resultSet);
        return new Location(id, location, country);
    }
}
