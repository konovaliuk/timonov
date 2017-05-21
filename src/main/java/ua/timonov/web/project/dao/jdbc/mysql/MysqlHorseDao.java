package ua.timonov.web.project.dao.jdbc.mysql;

import ua.timonov.web.project.dao.daointerface.IHorseDao;
import ua.timonov.web.project.dao.jdbc.EntityDao;
import ua.timonov.web.project.model.horse.Horse;

import java.util.List;

public class MysqlHorseDao extends EntityDao<Horse> implements IHorseDao {

    private static final MysqlHorseDao instance = new MysqlHorseDao();

    private MysqlHorseDao() {
    }

    public static MysqlHorseDao getInstance() {
        return instance;
    }

    @Override
    public boolean save(Horse horse) {
        return false;
    }

    @Override
    public boolean delete(Horse horse) {
        return false;
    }

    @Override
    public Horse findById(long id) {
        return null;
    }

    @Override
    public List<Horse> findAll() {
        return null;
    }
}
