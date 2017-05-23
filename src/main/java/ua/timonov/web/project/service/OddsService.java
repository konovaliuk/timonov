package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.DaoFactory;
import ua.timonov.web.project.dao.DatabaseType;
import ua.timonov.web.project.dao.daointerface.OddsDao;
import ua.timonov.web.project.exception.ServiceLayerException;
import ua.timonov.web.project.model.bet.Odds;

public class OddsService {

    private static final Logger LOGGER = Logger.getLogger(BetService.class);
    private static final OddsService instance = new OddsService();

    private DaoFactory daoFactory = DaoFactory.getFactory(DatabaseType.MYSQL);
    private OddsDao oddsDao = daoFactory.createOddsDao();

    private OddsService() {
    }

    public static OddsService getInstance() {
        return instance;
    }

    public Odds getById(long oddsId) {
        return oddsDao.findById(oddsId);
    }

    public void save(long horseInRaceId, Odds odds) throws ServiceLayerException {
        try {
            oddsDao.save(odds, horseInRaceId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ServiceLayerException("Odds saving failed!", e);
        }
    }

    public void delete(long oddsId) {
        try {
            oddsDao.delete(oddsId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ServiceLayerException("Odds deleting failed!", e);
        }
    }
}
