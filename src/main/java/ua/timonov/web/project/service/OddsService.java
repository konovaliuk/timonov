package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.JdbcDataManager;
import ua.timonov.web.project.dao.daointerface.OddsDao;
import ua.timonov.web.project.dao.jdbc.mysql.MysqlDaoFactory;
import ua.timonov.web.project.model.bet.Odds;

public class OddsService {

    private static final JdbcDataManager dataManager = JdbcDataManager.getInstance();
    private static final Logger LOGGER = Logger.getLogger(BetService.class);

    private OddsDao oddsDao = MysqlDaoFactory.getInstance().createOddsDao();

    public Odds getById(long oddsId) {
        return oddsDao.findById(oddsId);
    }

    public void save(long horseInRaceId, Odds odds) throws DataServiceException {
        try {
            oddsDao.save(odds, horseInRaceId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            // TODO - customize exception!
            throw new DataServiceException("Odds saving failed!", e);
        }
    }

    public void delete(long oddsId) {
        try {
            oddsDao.delete(oddsId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            // TODO - customize exception!
            throw new RuntimeException("Odds deleting failed!", e);
        }
    }
}
