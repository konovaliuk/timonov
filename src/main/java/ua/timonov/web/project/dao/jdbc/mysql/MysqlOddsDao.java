package ua.timonov.web.project.dao.jdbc.mysql;

import ua.timonov.web.project.dao.daointerface.IOddsDao;
import ua.timonov.web.project.dao.jdbc.EntityDao;
import ua.timonov.web.project.model.bet.Odds;

import java.util.List;

public class MysqlOddsDao extends EntityDao<Odds> implements IOddsDao {

    private static final MysqlOddsDao instance = new MysqlOddsDao();

    private MysqlOddsDao() {
    }

    public static MysqlOddsDao getInstance() {
        return instance;
    }

    @Override
    public boolean save(Odds odds) {
        return false;
    }

    @Override
    public boolean delete(Odds odds) {
        return false;
    }

    @Override
    public Odds findById(long id) {
        return null;
    }

    @Override
    public List<Odds> findAll() {
        return null;
    }
}
