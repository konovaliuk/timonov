package ua.timonov.web.project.dao.jdbc.mysql;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.daointerface.CountryDao;
import ua.timonov.web.project.dao.jdbc.EntityDao;
import ua.timonov.web.project.model.location.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlCountryDao extends EntityDao<Country> implements CountryDao {

    public static final int NAME_INDEX = 1;
    public static final int ID_INDEX = 2;
    public static final String ENTITY_NAME = "country";

    private static final Logger LOGGER = Logger.getLogger(MysqlCountryDao.class);
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

/*public List<Country> findAll() {
        String sql = "SELECT id AS country_id, name AS country FROM country";
//        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            LOGGER.info(ps.toString());
            List<Country> result = new ArrayList<>();
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

    /*@Override
    public boolean save(Country country) {
        return false;
    }

    @Override
    public boolean delete(Country country) {
        return false;
    }

    @Override
    public Country findById(long id) {
        return null;
    }

    @Override
    public List<Country> findAll() {
        return null;
    }*/