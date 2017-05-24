package ua.timonov.web.project.dao.jdbc;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.Dao;
import ua.timonov.web.project.dao.DataSourceFactory;
import ua.timonov.web.project.dao.Entity;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public abstract class EntityDao<T extends Entity> implements Dao<T> {

    public static final String FIND_ALL = "findAll";
    public static final String FIND_BY_ID = "findById";
    public static final String INSERT = "insert";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";

    protected String entityName;
    protected DataSource dataSource = DataSourceFactory.getInstance().getDataSource();

    private static final Logger LOGGER = Logger.getLogger(EntityDao.class);
    private static final ResourceBundle QUERIES = ResourceBundle.getBundle("queries");
    //    private static final JdbcDataManager dataManager = JdbcDataManager.getInstance();

    public EntityDao(String entityName) {
        this.entityName = entityName;
    }

    public String getName() {
        return entityName;
    }

    @Override
    public boolean save(T entity, long... externalId) {
        if (entity.getId() == 0) {
//            long id = insert(entity, externalId);
//            entity.setId(id);
            return insert(entity, externalId);
        } else {
            return update(entity, externalId);
        }
    }

    private boolean update(T entity, long... externalId) {
        String sql = getQuery(UPDATE);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            LOGGER.info(statement.toString());
            setEntityToParameters(entity, statement, externalId);
            statement.execute();
            int rowUpdated = statement.getUpdateCount();
            LOGGER.info(rowUpdated + " row(s) updated");
            return rowUpdated > 0;
        } catch (SQLException e) {
            LOGGER.error("Database error while updating table " + entityName +
                    ", exception message: " + e.getMessage());
            return false;
        }
    }

    private boolean insert(T entity, long... externalId) {
        String sql = getQuery(INSERT);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            LOGGER.info(statement.toString());
            setEntityToParameters(entity, statement, externalId);
            statement.executeUpdate();
            int rowInserted = statement.executeUpdate();
            LOGGER.info(rowInserted + " row(s) inserted");
            return rowInserted > 0;
            /*try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                generatedKeys.next();
                return generatedKeys.getLong(1);
            }*/
        } catch (SQLException e) {
            LOGGER.error("Database error while inserting into table " + entityName +
                    ", exception message: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(long id) {
        String sql = getQuery(DELETE);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, id);
            LOGGER.info(statement.toString());
            statement.execute();
            int rowUpdated = statement.getUpdateCount();
            LOGGER.info(rowUpdated + " row(s) deleted");
            return statement.getUpdateCount() > 0;
        } catch (SQLException e) {
            LOGGER.error("Database error while deleting from table " + entityName +
                    ", exception message: " + e.getMessage());
            return false;
        }
    }


    @Override
    public List<T> findAll() {
        String sql = getQuery(FIND_ALL);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            LOGGER.info(statement.toString());
            List<T> result = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(getEntityFromResultSet(resultSet));
                }
            }
            LOGGER.info(result.size() + " records founded");
            return result;
            // TODO
//            return new QueryResult<>(result, result.size());
        } catch (SQLException e) {
            LOGGER.error("Database error while searching in table " + entityName +
                    ", exception message: " + e.getMessage());
            return null;
        }
    }

    @Override
    public T findById(long id) {
        String sql = getQuery(FIND_BY_ID);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            LOGGER.info(statement.toString());
            T result = null;
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = getEntityFromResultSet(resultSet);
                }
            }
            if (result != null) {
                LOGGER.info("Item with id = " + id + " founded in table " + entityName);
            } else {
                LOGGER.info("Item with id = " + id + " not founded in table " + entityName);
            }
            return result;
//            return new QueryResult<T>(result, result.size());
        } catch (SQLException e) {
            LOGGER.error("Database error while searching in table " + entityName + " record with id = " + id +
                    ", exception message: " + e.getMessage());
            return null;
        }
    }

    protected String getQuery(String queryName) {
        return QUERIES.getString(entityName + "." + queryName);
    }

    protected abstract T getEntityFromResultSet(ResultSet resultSet) throws SQLException;

    protected abstract void setEntityToParameters(T entity, PreparedStatement statement, long... externalId)
            throws SQLException;

    protected String convertToEnumNameType(String stringFromDatabase) {
        return stringFromDatabase.toUpperCase().replace(' ', '_');
    }
}
