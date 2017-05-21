package ua.timonov.web.project.dao.jdbc;

import org.apache.log4j.Logger;
import ua.timonov.web.project.model.location.Country;
import ua.timonov.web.project.model.location.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationDao extends EntityDao {
    private static final Logger LOGGER = Logger.getLogger(LocationDao.class);

    private CountryDao countryDao = new CountryDao();

    public List<Location> getAll() {
        String sql = "SELECT location.id AS location_id, location.name AS location, " +
                "country.id AS country_id, country.name AS country FROM location\n" +
                "INNER JOIN country ON location.country_id = country.id";
//        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            LOGGER.info(ps.toString());
            List<Location> result = new ArrayList<>();
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    result.add(getEntityFromResultSet(resultSet));
                }
            }
            return result;
//            return new QueryResult<>(result, result.size());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Database operation failed! " + e.getMessage());
        }
    }

    public Location getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("location_id");
        String location = resultSet.getString("location");
        Country country = countryDao.getEntityFromResultSet(resultSet);
        return new Location(id, location, country);
    }

    public Location getById(long locationId) {
        String sql = "SELECT location.id AS location_id, location.name AS location, " +
                "country.id AS country_id, country.name AS country FROM location\n" +
                "INNER JOIN country ON location.country_id = country.id";
//        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            LOGGER.info(ps.toString());
            Location result = null;
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    result = getEntityFromResultSet(resultSet);
                }
            }
            return result;
//            return new QueryResult<>(result, result.size());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Database operation failed! " + e.getMessage());
        }
    }
}
