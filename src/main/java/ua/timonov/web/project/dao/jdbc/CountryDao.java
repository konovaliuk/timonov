package ua.timonov.web.project.dao.jdbc;

import org.apache.log4j.Logger;
import ua.timonov.web.project.model.location.Country;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDao {
    private static final Logger LOGGER = Logger.getLogger(CountryDao.class);

    public Country getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("country_id");
        String country = resultSet.getString("country");
        return new Country(id, country);
    }
}
