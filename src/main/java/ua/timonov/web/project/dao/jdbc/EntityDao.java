package ua.timonov.web.project.dao.jdbc;

import ua.timonov.web.project.dao.DataSourceFactory;
import ua.timonov.web.project.dao.IDao;

import javax.sql.DataSource;
import java.util.List;

public abstract class EntityDao<Entity> implements IDao<Entity> {
    protected DataSource dataSource = DataSourceFactory.getInstance().getDataSource();

    protected String transformToConstantView(String stringFromDatabase) {
        return stringFromDatabase.toUpperCase().replace(' ', '_');
    }

    @Override
    public boolean save(Entity entity) {
        return false;
    }

    @Override
    public boolean delete(Entity entity) {
        return false;
    }

    @Override
    public Entity findById(long id) {
        return null;
    }

    @Override
    public List<Entity> findAll() {
        return null;
    }
}
