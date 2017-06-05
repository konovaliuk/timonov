package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.daointerface.BetDao;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.bet.BetStatus;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.race.RaceStatus;
import ua.timonov.web.project.model.user.Money;

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

    public void makeBet(Bet bet) {
        Race race = raceService.findByHorseInRaceId(bet.getOdds().getHorseInRaceId());
        if (race.getRaceStatus() == RaceStatus.OPEN_TO_BET) {
            userService.deductUserBalance(bet.getUser(), bet.getSum());
            raceService.increaseBetSum(race, bet.getSum());
            bet.setBetStatus(BetStatus.MADE);
            save(bet);
        } else {
            LOGGER.warn("Race is not open for bet. You can not make bet on it");
            throw new ServiceException("Race is not open for bet. You can not make bet on it");
        }
    }

    public void cancelBet(Bet bet, Race race) {
        userService.returnMoney(bet);
        raceService.decreaseBetSum(race, bet.getSum());
        bet.setBetStatus(BetStatus.CANCELLED);
        save(bet);
    }

    public Money payWin(Bet bet) {
        Money paidSum = userService.getWin(bet);
        bet.setBetStatus(BetStatus.PAID);
        save(bet);
        return paidSum;
    }

    public List<Bet> findListByUser(Long userId) {
        List<Bet> userBets = betDao.findListByUserId(userId);
        if (userBets == null) {
            LOGGER.error("Database error while searching in table " + betDao.getName());
            throw new ServiceException("Database error while searching in table " + betDao.getName());
        }
        return userBets;
    }
}
