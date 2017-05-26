package ua.timonov.web.project.service;

import ua.timonov.web.project.dao.daointerface.BetDao;
import ua.timonov.web.project.dao.daointerface.UserDao;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.user.User;

public class UserService extends DataService<User, Bet> {

    private static UserDao userDao = daoFactory.createUserDao();
    private static BetDao betDao = daoFactory.createBetDao();
    private static final UserService instance = new UserService();

    private UserService() {
        super(userDao, betDao);
    }

    public static UserService getInstance() {
        return instance;
    }
}
