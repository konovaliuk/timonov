package ua.timonov.web.project.dao.jdbc.mysql;

import ua.timonov.web.project.dao.daointerface.ICountryDao;
import ua.timonov.web.project.dao.jdbc.EntityDao;
import ua.timonov.web.project.model.location.Country;

import java.util.List;

public class MysqlCountryDao extends EntityDao<Country> implements ICountryDao {
    private static final MysqlCountryDao instance = new MysqlCountryDao();

    private MysqlCountryDao() {
    }

    public static MysqlCountryDao getInstance() {
        return instance;
    }

    @Override
    public boolean save(Country country) {
        return false;
    }

    @Override
    public boolean delete(Country country) {
        return false;
    }

    @Override
    public Country findById(long id) {
        return null;
    }

    @Override
    public List<Country> findAll() {
        return null;
    }
}
