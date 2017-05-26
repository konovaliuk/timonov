package ua.timonov.web.project.service;

import ua.timonov.web.project.dao.daointerface.BetDao;
import ua.timonov.web.project.dao.daointerface.OddsDao;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.bet.Odds;

import java.util.List;

public class OddsService extends DataService<Odds, Bet> {

    private static OddsDao oddsDao = daoFactory.createOddsDao();
    private static BetDao betDao = daoFactory.createBetDao();
    private static final OddsService instance = new OddsService();

    private OddsService() {
        super(oddsDao, betDao);
    }

    public static OddsService getInstance() {
        return instance;
    }

    public void save(Odds odds) throws ServiceException {
        List<Odds> listStoredOdds = oddsDao.findListByHorseInRace(odds.getHorseInRaceId());
        for (Odds storedOdd : listStoredOdds) {
            if (storedOdd.getBetType().equals(odds.getBetType())) {
                odds.setId(storedOdd.getId());
            }
        }
        super.save(odds);
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