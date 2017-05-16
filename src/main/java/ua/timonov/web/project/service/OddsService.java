package ua.timonov.web.project.service;

import ua.timonov.web.project.dao.JdbcDataManager;
import ua.timonov.web.project.dao.jdbc.OddsDao;
import ua.timonov.web.project.model.bet.Odds;

public class OddsService {

    private static final JdbcDataManager dataManager = JdbcDataManager.getInstance();

    private OddsDao oddsDao = new OddsDao();

    public Odds getById(long oddsId) {
        return oddsDao.getById(oddsId);
    }
}
