package ua.timonov.web.project.dao.jdbc;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.Dao;
import ua.timonov.web.project.dao.DataSourceFactory;
import ua.timonov.web.project.dao.Entity;
import ua.timonov.web.project.util.LoggerMessages;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Abstract implementation of DAO interface
 * @param <T>       entity type
 */
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

    /* updates existing entity in database */
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
            LOGGER.error(LoggerMessages.DB_ERROR_UPDATE + entityName + LoggerMessages.EXCEPTION_MESSAGE + e.getMessage());
            return false;
        }
    }

    /* inserts new entity to database */
    private long insert(T entity) {
        String sql = getQuery(INSERT);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            LOGGER.info(statement.toString());
            setEntityToParameters(entity, statement);
            int rowInserted = statement.executeUpdate();
            LOGGER.info(rowInserted + LoggerMessages.ROWS_INSERTED_INTO + entityName);
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                generatedKeys.next();
                return generatedKeys.getLong(1);
            }
        } catch (SQLException e) {
            LOGGER.error(LoggerMessages.DB_ERROR_INSERT + entityName + LoggerMessages.EXCEPTION_MESSAGE + e.getMessage());
            return 0;
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = getQuery(DELETE);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            LOGGER.info(statement.toString());
            int rowUpdated = statement.executeUpdate();
            LOGGER.info(rowUpdated + LoggerMessages.ROWS_DELETED);
            return statement.getUpdateCount() > 0;
        } catch (SQLException e) {
            LOGGER.error(LoggerMessages.DB_ERROR_DELETE + entityName + LoggerMessages.EXCEPTION_MESSAGE + e.getMessage());
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
            LOGGER.info(result.size() + LoggerMessages.ITEMS_FOUND);
            return result;
        } catch (SQLException e) {
            LOGGER.error(LoggerMessages.DB_ERROR_SEARCH + entityName + LoggerMessages.EXCEPTION_MESSAGE + e.getMessage());
            return null;
        }
    }

    @Override
    public T findById(Long id) {
        String sql = getQuery(FIND_ALL) + " " + getQuery(FIND_BY_ID);
        return findByIdWithSql(id, sql, "");
    }

    @Override
    public T findByForeignId(Long id, String foreignKeyEntityName) {
        String sql = getQuery(FIND_ALL) + " " + getQuery(FIND_BY + foreignKeyEntityName);
        return findByIdWithSql(id, sql, foreignKeyEntityName);
    }

    /* finds list of entities by ID of some other entity */
    protected List<T> findListWithSql(String sql, Long foreignId) {
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
            LOGGER.info(result.size() + LoggerMessages.ITEMS_FOUND);
            return result;
        } catch (SQLException e) {
            LOGGER.error(LoggerMessages.DB_ERROR_SEARCH + entityName + LoggerMessages.EXCEPTION_MESSAGE + e.getMessage());
            return null;
        }
    }

    /* finds list of entities by ID of some other entity */
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
                LOGGER.info(LoggerMessages.ITEM + otherEntity + LoggerMessages.WITH_ID + id +
                        LoggerMessages.FOUND_IN_TABLE + entityName);
            } else {
                LOGGER.info(LoggerMessages.ITEM + otherEntity + LoggerMessages.WITH_ID + id +
                        LoggerMessages.NOT_FOUND_IN_TABLE + entityName);
            }
            return result;
        } catch (SQLException e) {
            LOGGER.error(LoggerMessages.DB_ERROR_SEARCH + entityName + LoggerMessages.ITEM_WITH_ID + id +
                    LoggerMessages.EXCEPTION_MESSAGE + e.getMessage());
            return null;
        }
    }

    /* fetches SQL query from property file by short query name */
    protected String getQuery(String queryName) {
        return QUERIES.getString(entityName + "." + queryName);
    }

    /* fetches suffix for query if necessary */
    protected String getQuerySuffixOrderBy() {
        return "";
    }

    /* retrieves entity from result set */
    protected abstract T getEntityFromResultSet(ResultSet resultSet) throws SQLException;

    /* sets entity properties to prepare statement parameters */
    protected abstract void setEntityToParameters(T entity, PreparedStatement statementExternalId)
            throws SQLException;

    /* converts names of enum names to Java style */
    protected String convertToEnumNameType(String stringFromDatabase) {
        return stringFromDatabase.toUpperCase().replace(' ', '_');
    }
}
