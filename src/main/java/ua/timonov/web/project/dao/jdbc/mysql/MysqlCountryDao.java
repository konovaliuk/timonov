package ua.timonov.web.project.dao.jdbc.mysql;

import ua.timonov.web.project.dao.daointerface.CountryDao;
import ua.timonov.web.project.dao.jdbc.EntityDao;
import ua.timonov.web.project.model.location.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MySql implementation for CountryDao interface
 */
public class MysqlCountryDao extends EntityDao<Country> implements CountryDao {

    public static final int NAME_INDEX = 1;
    public static final int ID_INDEX = 2;
    public static final String ENTITY_NAME = "Country";

    private static final MysqlCountryDao instance = new MysqlCountryDao();

    private MysqlCountryDao() {
        super(ENTITY_NAME);
    }

    public static MysqlCountryDao getInstance() {
        return instance;
    }

    public Country getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("country_id");
        String country = resultSet.getString("country");
        return new Country(id, country);
    }

    @Override
    protected void setEntityToParameters(Country country, PreparedStatement statement)
            throws SQLException {

        statement.setString(NAME_INDEX, country.getName());
        if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
            statement.setLong(ID_INDEX, country.getId());
        }
    }
}