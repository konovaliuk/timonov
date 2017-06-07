package ua.timonov.web.project.dao.jdbc.mysql;

import ua.timonov.web.project.dao.daointerface.LocationDao;
import ua.timonov.web.project.dao.jdbc.EntityDao;
import ua.timonov.web.project.model.location.Country;
import ua.timonov.web.project.model.location.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MySql implementation for LocationDao interface
 */
public class MysqlLocationDao extends EntityDao<Location> implements LocationDao {

    public static final int COUNTRY_ID_INDEX = 1;
    public static final int NAME_INDEX = 2;
    public static final int ID_INDEX = 3;
    public static final String ENTITY_NAME = "Location";

    private static final MysqlLocationDao instance = new MysqlLocationDao();

    private MysqlLocationDao() {
        super(ENTITY_NAME);
    }

    public static MysqlLocationDao getInstance() {
        return instance;
    }

    public Location getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("location_id");
        String location = resultSet.getString("location");
        Country country = getCountryFromResultSet(resultSet);
        return new Location(id, location, country);
    }

    private Country getCountryFromResultSet(ResultSet resultSet) throws SQLException {
        MysqlCountryDao mysqlCountryDao = MysqlCountryDao.getInstance();
        return mysqlCountryDao.getEntityFromResultSet(resultSet);
    }

    @Override
    protected void setEntityToParameters(Location location, PreparedStatement statement)
            throws SQLException {

        statement.setLong(COUNTRY_ID_INDEX, location.getCountry().getId());
        statement.setString(NAME_INDEX, location.getName());
        if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
            statement.setLong(ID_INDEX, location.getId());
        }
    }
}