package ua.timonov.web.project.dao.jdbc.mysql;

import ua.timonov.web.project.dao.daointerface.ILocationDao;
import ua.timonov.web.project.dao.jdbc.EntityDao;
import ua.timonov.web.project.model.location.Location;

import java.util.List;

public class MySqlLocationDao extends EntityDao<Location> implements ILocationDao {

    private static final MySqlLocationDao instance = new MySqlLocationDao();

    private MySqlLocationDao() {
    }

    public static MySqlLocationDao getInstance() {
        return instance;
    }

    @Override
    public boolean save(Location location) {
        return false;
    }

    @Override
    public boolean delete(Location location) {
        return false;
    }

    @Override
    public Location findById(long id) {
        return null;
    }

    @Override
    public List<Location> findAll() {
        return null;
    }
}
