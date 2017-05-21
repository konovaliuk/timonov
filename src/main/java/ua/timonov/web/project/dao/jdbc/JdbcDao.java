package ua.timonov.web.project.dao.jdbc;

import ua.timonov.web.project.dao.IDao;

import java.util.List;

public abstract class JdbcDao<Entity> implements IDao<Entity> {

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
