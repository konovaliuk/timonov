package ua.timonov.web.project.dao.jdbc.mysql;

import ua.timonov.web.project.dao.daointerface.IBetDao;
import ua.timonov.web.project.dao.jdbc.EntityDao;
import ua.timonov.web.project.model.bet.Bet;

import java.util.List;

public class MysqlBetDao extends EntityDao<Bet> implements IBetDao {

    private static final MysqlBetDao instance = new MysqlBetDao();

    private MysqlBetDao() {
    }

    public static MysqlBetDao getInstance() {
        return instance;
    }

    @Override
    public boolean save(Bet bet) {
        return false;
    }

    @Override
    public boolean delete(Bet bet) {
        return false;
    }

    @Override
    public Bet findById(long id) {
        return null;
    }

    @Override
    public List<Bet> findAll() {
        return null;
    }
}
