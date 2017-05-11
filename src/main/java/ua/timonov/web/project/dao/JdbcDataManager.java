package ua.timonov.web.project.dao;

import org.apache.log4j.Logger;

import javax.sql.DataSource;

public class JdbcDataManager {

    private static final Logger LOGGER = Logger.getLogger(JdbcDataManager.class);
    private static final JdbcDataManager instance = new JdbcDataManager();

    private DataSource dataSource = DataSourceFactory.getInstance().getDataSource();

    private JdbcDataManager() {
    }

    public static JdbcDataManager getInstance() {
        return instance;
    }
}
