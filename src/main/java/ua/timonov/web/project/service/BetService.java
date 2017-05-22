package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.daointerface.BetDao;
import ua.timonov.web.project.dao.jdbc.mysql.MysqlDaoFactory;
import ua.timonov.web.project.model.bet.Bet;

public class BetService {

    private static final Logger LOGGER = Logger.getLogger(BetService.class);

    private BetDao betDao = MysqlDaoFactory.getInstance().createBetDao();

    public void save(Bet bet) throws DataServiceException {
        try {
            betDao.save(bet);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            // TODO - customize exception!
            throw new DataServiceException("Bet saving failed!", e);
        }

    }
}
