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

    public void makeBet(Bet bet, Race race) throws ServiceException {
        if (race.getRaceStatus() == RaceStatus.OPEN_TO_BET) {
            userService.deductUserBalance(bet.getUser(), bet.getSum());
            raceService.increaseBetSum(race, bet.getSum());
            bet.setBetStatus(BetStatus.MADE);
            save(bet);
        } else {
            String message = ExceptionMessages.getMessage(ExceptionMessages.RACE_NOT_OPEN);
            LOGGER.warn(message);
            throw new ServiceException(message);
        }
    }

    public void cancelBet(Bet bet, Race race) throws ServiceException {
        userService.returnMoney(bet);
        raceService.decreaseBetSum(race, bet.getSum());
        bet.setBetStatus(BetStatus.CANCELLED);
        save(bet);
    }

    public Money payWin(Bet bet) throws ServiceException {
        Money paidSum = userService.getWin(bet);
        bet.setBetStatus(BetStatus.PAID);
        save(bet);
        return paidSum;
    }

    public List<Bet> findListByUser(Long userId) throws ServiceException {
        List<Bet> userBets = betDao.findListByUserId(userId);
        if (userBets == null) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.FIND_ITEMS_FAILED + " " + betDao.getName());
            LOGGER.error(message);
            throw new ServiceException(message);
        }
        return userBets;
    }

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
