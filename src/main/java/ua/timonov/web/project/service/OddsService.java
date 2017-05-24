package ua.timonov.web.project.service;

import ua.timonov.web.project.dao.Dao;
import ua.timonov.web.project.dao.daointerface.OddsDao;
import ua.timonov.web.project.model.bet.Odds;

public class OddsService extends DataService<Odds> {

    private static OddsDao oddsDao = daoFactory.createOddsDao();
    private static final OddsService instance = new OddsService(oddsDao);

    private OddsService(Dao<Odds> oddsDao) {
        super(oddsDao, "Odds");
    }

    public static OddsService getInstance() {
        return instance;
    }
}


    /*public Odds findById(long oddsId) {
        return oddsDao.findById(oddsId);
    }

    public void save(long horseInRaceId, Odds odds) throws ServiceException {
        try {
            oddsDao.save(odds, horseInRaceId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException("Odds saving failed!", e);
        }
    }

    public void delete(long oddsId) {
        try {
            oddsDao.delete(oddsId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException("Odds deleting failed!", e);
        }
    }*/