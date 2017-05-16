package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.jdbc.BetDao;
import ua.timonov.web.project.model.bet.Bet;

public class BetService {

    private static final Logger LOGGER = Logger.getLogger(BetService.class);

    BetDao betDao = new BetDao();

    public void save(Bet bet) {
        try {
            betDao.save(bet);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            // TODO - customize!
            throw new RuntimeException("Bet saving failed!", e);
        }

    }
}
