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
    public static final String ORDER_BY = "orderBy";
    public static final String FIND_BY_ID = "findById";
    public static final String FIND_BY = "findBy";
    public static final String INSERT = "insert";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String SPACE = " ";

    protected static final ResourceBundle QUERIES = ResourceBundle.getBundle("queries");
    protected String entityName;
    protected DataSource dataSource = DataSourceFactory.getInstance().getDataSource();

    private static final Logger LOGGER = Logger.getLogger(EntityDao.class);

    public EntityDao(String entityName) {
        this.entityName = entityName;
    }

    public String getName() {
        return entityName;
    }

    @Override
    public boolean save(T entity) {
        if (entity.getId() > 0) {
            return update(entity);
        } else {
            long id = insert(entity);
            if (id > 0) {
                entity.setId(id);
                return true;
            }
            else return false;
        }
    }

    private boolean update(T entity) {
        String sql = getQuery(UPDATE);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            LOGGER.info(statement.toString());
            setEntityToParameters(entity, statement);
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

    private long insert(T entity) {
        String sql = getQuery(INSERT);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            LOGGER.info(statement.toString());
            setEntityToParameters(entity, statement);
            int rowInserted = statement.executeUpdate();
            LOGGER.info(rowInserted + " row(s) inserted into " + entityName);
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                generatedKeys.next();
                return generatedKeys.getLong(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Database error while inserting into table " + entityName +
                    ", exception message: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public boolean delete(long id) {
        String sql = getQuery(DELETE);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            LOGGER.info(statement.toString());
            int rowUpdated = statement.executeUpdate();
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
        String sql = getQuery(FIND_ALL) + " " + getQuerySuffixOrderBy();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            LOGGER.info(statement.toString());
            List<T> result = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(getEntityFromResultSet(resultSet));
                }
            }
            LOGGER.info(result.size() + " items founded");
            return result;
            // TODO
//            return new QueryResult<>(result, result.size());
        } catch (SQLException e) {
            LOGGER.error("Database error while searching in table " + entityName +
                    ", exception message: " + e.getMessage());
            return null;
        }
    }

    protected List<T> findListWithSql(String sql, long foreignId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, foreignId);
            LOGGER.info(statement.toString());
            List<T> result = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(getEntityFromResultSet(resultSet));
                }
            }
            LOGGER.info(result.size() + " items founded");
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
        String sql = getQuery(FIND_ALL) + " " + getQuery(FIND_BY_ID);
        return findByIdWithSql(id, sql, "");
    }

    @Override
    public T findByForeignId(long id, String foreignKeyEntityName) {
        String sql = getQuery(FIND_ALL) + " " + getQuery(FIND_BY + foreignKeyEntityName);
        return findByIdWithSql(id, sql, foreignKeyEntityName);
    }

    protected T findByIdWithSql(long id, String sql, String otherEntity) {
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
                LOGGER.info("Item " + otherEntity + " with id = " + id + " found in table " + entityName);
            } else {
                LOGGER.info("Item with id = " + id + " not found in table " + entityName);
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

    protected String getQuerySuffixOrderBy() {
        return "";
    }

    protected abstract T getEntityFromResultSet(ResultSet resultSet) throws SQLException;

    protected abstract void setEntityToParameters(T entity, PreparedStatement statementexternalId)
            throws SQLException;

    protected String convertToEnumNameType(String stringFromDatabase) {
        return stringFromDatabase.toUpperCase().replace(' ', '_');
    }
}
