package ua.timonov.web.project.dao.jdbc;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.DataSourceFactory;
import ua.timonov.web.project.dao.Dao;
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

    protected String name;
    protected DataSource dataSource = DataSourceFactory.getInstance().getDataSource();

    private static final Logger LOGGER = Logger.getLogger(EntityDao.class);
    private static final ResourceBundle QUERIES = ResourceBundle.getBundle("queries");
    //    private static final JdbcDataManager dataManager = JdbcDataManager.getInstance();

    // TODO remove!
    public EntityDao() {
    }

    public EntityDao(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean save(T entity, long... externalId) {
        if (entity.getId() == 0) {
            long id = insert(entity);
            entity.setId(id);
            return id != 0;
        } else {
            return update(entity);
        }
    }

    private boolean update(T entity) {
        String sql = getQuery(UPDATE);
//        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setEntityToParameters(entity, statement);
            statement.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Database operation failed! " + e.getMessage());
        }
    }

    private long insert(T entity) {
        String sql = getQuery(INSERT);
//        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setEntityToParameters(entity, statement);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                generatedKeys.next();
                return generatedKeys.getLong(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Database operation failed! " + e.getMessage());
        }
    }

    @Override
    public boolean delete(long id) {
        String sql = getQuery(DELETE);
//        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, id);
            statement.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Database operation failed! " + e.getMessage());
        }
    }


    @Override
    public List<T> findAll() {
        String sql = getQuery(FIND_ALL);
//        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            LOGGER.info(ps.toString());
            List<T> result = new ArrayList<>();
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    result.add(getEntityFromResultSet(resultSet));
                }
            }
            return result;
            // TODO
//            return new QueryResult<>(result, result.size());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Database operation failed! " + e.getMessage());
        }
    }

    @Override
    public T findById(long id) {
        String sql = getQuery(FIND_BY_ID);
//        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);

            LOGGER.info(ps.toString());
            T result = null;
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result = getEntityFromResultSet(resultSet);
                }
            }
            return result;
//            return new QueryResult<T>(result, result.size());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Database operation failed! " + e.getMessage());
        }
    }

    protected String getQuery(String queryName) {
        return QUERIES.getString(name + "." + queryName);
    }

    protected abstract T getEntityFromResultSet(ResultSet resultSet) throws SQLException;

    protected abstract void setEntityToParameters(T entity, PreparedStatement statement, long... externalId)
            throws SQLException;

    protected String transformToConstantView(String stringFromDatabase) {
        return stringFromDatabase.toUpperCase().replace(' ', '_');
    }
}
