package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.daointerface.BetDao;
import ua.timonov.web.project.dao.daointerface.OddsDao;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.bet.Odds;
import ua.timonov.web.project.util.ExceptionMessages;

import java.util.List;

/**
 * Represents service for interact with DAO layer interface OddsDao and perform some logic with other DAO
 */
public class OddsService extends DataService<Odds, Bet> {

    private static final Logger LOGGER = Logger.getLogger(HorseService.class);
    private static OddsDao oddsDao = daoFactory.createOddsDao();
    private static BetDao betDao = daoFactory.createBetDao();
    private static final OddsService instance = new OddsService();

    private OddsService() {
        super(oddsDao, betDao);
    }

    public static OddsService getInstance() {
        return instance;
    }

    /**
     * saves odds to database, if there is the odds with same type, updates it
     * @param odds
     * @throws ServiceException
     */
    public void save(Odds odds) throws ServiceException {
        List<Odds> listStoredOdds = oddsDao.findListByHorseInRace(odds.getHorseInRaceId());
        for (Odds storedOdd : listStoredOdds) {
            if (storedOdd.getBetType().equals(odds.getBetType())) {
                odds.setId(storedOdd.getId());
            }
        }
        super.save(odds);
    }

    /**
     * checks if user (bookie) inputted not valid values for odds (total chances is less than wining chances)
     * @param odds
     * @throws ServiceException
     */
    public void validateOddsRates(Odds odds) throws ServiceException {
        if (odds.getTotal() <= odds.getChances()) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.ODDS_CHANCES_MORE_TOTAL);
            LOGGER.error(message);
            throw new ServiceException(message);
        }
    }
}