package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.daointerface.BetDao;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.bet.BetStatus;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.race.RaceStatus;
import ua.timonov.web.project.model.user.Money;
import ua.timonov.web.project.util.ExceptionMessages;

import java.util.List;

/**
 * Represents service for interact with DAO layer interface BetDao and perform some logic with other services
 */
public class BetService extends DataService<Bet, Bet> {

    private static final Logger LOGGER = Logger.getLogger(BetService.class);
    private static BetDao betDao = daoFactory.createBetDao();
    private static UserService userService = ServiceFactory.getInstance().createUserService();
    private static RaceService raceService = ServiceFactory.getInstance().createRaceService();

    private static BetService instance;

    private BetService() {
        super(betDao);
    }

    public static BetService getInstance() {
        if (instance == null) {
            instance = new BetService();
        }
        return instance;
    }

    /**
     * performs actions for make new bet (decrease user balance, increase race bet sum) and saves new bet
     * @param bet
     * @param race
     * @throws ServiceException
     */
    public void makeBet(Bet bet, Race race) throws ServiceException {
        if (race.getRaceStatus() == RaceStatus.OPEN_TO_BET) {
            userService.reduceUserBalance(bet.getUser(), bet.getSum());
            raceService.increaseBetSum(race, bet.getSum());
            bet.setBetStatus(BetStatus.MADE);
            save(bet);
        } else {
            String message = ExceptionMessages.getMessage(ExceptionMessages.RACE_NOT_OPEN);
            LOGGER.warn(message);
            throw new ServiceException(message);
        }
    }

    /**
     * performs actions for cancelling bet and saves it with new status
     * @param bet
     * @param race
     * @throws ServiceException
     */
    public void cancelBet(Bet bet, Race race) throws ServiceException {
        userService.returnMoney(bet.getUser().getAccount().getId(), bet.getSum());
        raceService.decreaseBetSum(race, bet.getSum());
        bet.setBetStatus(BetStatus.CANCELLED);
        save(bet);
    }

    /**
     * finds sum to pay on won bet and returns it
     * @param bet
     * @return
     * @throws ServiceException
     */
    public Money payWin(Bet bet) throws ServiceException {
        Money paidSum = userService.getWin(bet);
        bet.setBetStatus(BetStatus.PAID);
        save(bet);
        return paidSum;
    }

    /**
     * finds bets by user ID
     * @param userId
     * @return
     * @throws ServiceException
     */
    public List<Bet> findListByUser(Long userId) throws ServiceException {
        List<Bet> userBets = betDao.findListByUserId(userId);
        if (userBets == null) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.FIND_ITEMS_FAILED + " " + betDao.getName());
            LOGGER.error(message);
            throw new ServiceException(message);
        }
        return userBets;
    }

    /**
     * cancels bet if it's possible and saves its new status
     * @param betId
     * @throws ServiceException
     */
    public void cancelBetById(Long betId) throws ServiceException {
        Bet bet = findById(betId);
        Long horseInRaceId = bet.getOdds().getHorseInRaceId();
        Race race = raceService.findByHorseInRaceId(horseInRaceId);
        if (race.getRaceStatus().ordinal() < RaceStatus.FINISHED.ordinal() && bet.getBetStatus() == BetStatus.MADE) {
            cancelBet(bet, race);
        } else {
            String message = ExceptionMessages.getMessage(ExceptionMessages.BET_CANT_BE_CANCELLED);
            LOGGER.error(message);
            throw new ServiceException(message);
        }
    }
}
