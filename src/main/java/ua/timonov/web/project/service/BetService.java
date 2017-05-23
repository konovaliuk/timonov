package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.DaoFactory;
import ua.timonov.web.project.dao.DatabaseType;
import ua.timonov.web.project.dao.daointerface.BetDao;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.exception.ServiceLayerException;
import ua.timonov.web.project.model.bet.Bet;

public class BetService {

    private static final Logger LOGGER = Logger.getLogger(BetService.class);
    private static final BetService instance = new BetService();

    private DaoFactory daoFactory = DaoFactory.getFactory(DatabaseType.MYSQL);
    private BetDao betDao = daoFactory.createBetDao();

    private BetService() {
    }

    public static BetService getInstance() {
        return instance;
    }

    public void save(Bet bet) {
        try {
            betDao.save(bet);
        } catch (AppException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceLayerException("Bet saving failed!", e);
        }
    }
}
