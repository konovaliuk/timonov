package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.JdbcDataManager;
import ua.timonov.web.project.dao.jdbc.OddsDao;
import ua.timonov.web.project.model.bet.Odds;

public class OddsService {

    private static final JdbcDataManager dataManager = JdbcDataManager.getInstance();
    private static final Logger LOGGER = Logger.getLogger(BetService.class);

    private OddsDao oddsDao = new OddsDao();

    public Odds getById(long oddsId) {
        return oddsDao.getById(oddsId);
    }

    public void save(long horseInRaceId, Odds odds) {
        try {
            oddsDao.save(horseInRaceId, odds);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            // TODO - customize exception!
            throw new RuntimeException("Odds saving failed!", e);
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
