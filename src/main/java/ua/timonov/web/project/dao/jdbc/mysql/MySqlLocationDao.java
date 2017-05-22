package ua.timonov.web.project.dao.jdbc.mysql;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.daointerface.LocationDao;
import ua.timonov.web.project.dao.jdbc.EntityDao;
import ua.timonov.web.project.model.location.Country;
import ua.timonov.web.project.model.location.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlLocationDao extends EntityDao<Location> implements LocationDao {

    public static final int COUNTRY_ID_INDEX = 1;
    public static final int NAME_INDEX = 2;
    public static final int ID_INDEX = 3;
    public static final String ENTITY_NAME = "location";

    private static final Logger LOGGER = Logger.getLogger(MySqlLocationDao.class);
    private static final MySqlLocationDao instance = new MySqlLocationDao();

    private MySqlLocationDao() {
        super(ENTITY_NAME);
    }

    public static MySqlLocationDao getInstance() {
        return instance;
    }

    public Location getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("location_id");
        String location = resultSet.getString("location");
        Country country = getCountryFromResultSet(resultSet);
        return new Location(id, location, country);
    }

    @Override
    protected void setEntityToParameters(Location location, PreparedStatement statement, long... externalId)
            throws SQLException {

        statement.setLong(COUNTRY_ID_INDEX, location.getCountry().getId());
        statement.setString(NAME_INDEX, location.getName());
        if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
            statement.setLong(ID_INDEX, location.getId());
        }
    }

    private Country getCountryFromResultSet(ResultSet resultSet) throws SQLException {
        MysqlCountryDao mysqlCountryDao = MysqlCountryDao.getInstance();
        return mysqlCountryDao.getEntityFromResultSet(resultSet);
    }
}

/*@Override
    public boolean save(Location location) {
        return false;
    }

    @Override
    public boolean delete(Location location) {
        return false;
    }

    @Override
    public Location findById(long id) {
        return null;
    }

    @Override
    public List<Location> findAll() {
        return null;
    }*/

    /*public List<Location> findAll() {
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
    }*/

    /*public Location findById(long locationId) {
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
    }*/