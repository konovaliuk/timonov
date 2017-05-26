package ua.timonov.web.project.service;

import ua.timonov.web.project.dao.daointerface.BetDao;
import ua.timonov.web.project.model.bet.Bet;

public class BetService extends DataService<Bet, Bet> {

    private static BetDao betDao = daoFactory.createBetDao();
    private static final BetService instance = new BetService();

    private BetService() {
        super(betDao);
    }

    public static BetService getInstance() {
        return instance;
    }
}
