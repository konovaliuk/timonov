package ua.timonov.web.project.dao.jdbc;

import ua.timonov.web.project.dao.DataSourceFactory;

import javax.sql.DataSource;

public class EntityDao {
    protected DataSource dataSource = DataSourceFactory.getInstance().getDataSource();

    protected String transformToConstantView(String stringFromDatabase) {
        return stringFromDatabase.toUpperCase().replace(' ', '_');
    }
}
