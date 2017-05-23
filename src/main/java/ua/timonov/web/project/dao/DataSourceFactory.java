package ua.timonov.web.project.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataSourceFactory {

    private static final DataSourceFactory instance = new DataSourceFactory();

    private static final Logger LOGGER = Logger.getLogger(DataSourceFactory.class);
    public static final String JDBC_PROPERTIES = "jdbc.properties";
    public static final String JDBC_DRIVER = "jdbc.driver";
    public static final String JDBC_USERNAME = "jdbc.username";
    public static final String JDBC_PASSWORD = "jdbc.password";
    public static final String JDBC_URL = "jdbc.url";
    public static final String POOL_SIZE = "jdbc.poolSize";

    private DataSource dataSource;

    private DataSourceFactory() {
        try {
            dataSource = initializeDataSource();
        } catch (Exception e) {
            LOGGER.error("Datasource init error: " + e.getMessage());
            throw new RuntimeException("Error in database connection initialization");
        }
    }

    public static DataSourceFactory getInstance() {
        return instance;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    private DataSource initializeDataSource() {
        ClassLoader classLoader = getClass().getClassLoader();
        Properties properties = new Properties();
        try (InputStream inputStream = classLoader.getResourceAsStream(JDBC_PROPERTIES)) {
            properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        BasicDataSource connectionPool = new BasicDataSource();
        connectionPool.setDriverClassName(properties.getProperty(JDBC_DRIVER));
        connectionPool.setUsername(properties.getProperty(JDBC_USERNAME));
        connectionPool.setPassword(properties.getProperty(JDBC_PASSWORD));
        connectionPool.setUrl(properties.getProperty(JDBC_URL));
        connectionPool.setInitialSize(Integer.valueOf(properties.getProperty(POOL_SIZE)));
        return connectionPool;
    }
}
