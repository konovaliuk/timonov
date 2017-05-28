package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.daointerface.BetDao;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.race.RaceStatus;

public class BetService extends DataService<Bet, Bet> {

    private static final Logger LOGGER = Logger.getLogger(BetService.class);
    private static BetDao betDao = daoFactory.createBetDao();
    private UserService userService = ServiceFactory.getInstance().createUserService();
    private RaceService raceService = ServiceFactory.getInstance().createRaceService();
    private static final BetService instance = new BetService();

    private BetService() {
        super(betDao);
    }

    public static BetService getInstance() {
        return instance;
    }

    public void tryToMakeBet(Bet bet) {
        Race race = raceService.findByHorseInRaceId(bet.getOdds().getHorseInRaceId());
        if (race.getRaceStatus().equals(RaceStatus.OPEN_TO_BET)) {
            userService.deductUserBalance(bet.getUser(), bet.getSum());
            save(bet);
        } else {
            LOGGER.warn("Race is not open for bet. You can not make bet on it");
            throw new SecurityException("Race is not open for bet. You can not make bet on it");
        }

    }
}
