package ua.timonov.web.project.service;

import ua.timonov.web.project.dao.daointerface.UserAccountDao;
import ua.timonov.web.project.dao.daointerface.UserDao;
import ua.timonov.web.project.model.user.Account;
import ua.timonov.web.project.model.user.User;

public class UserAccountService extends DataService<Account, User> {

    private static UserAccountDao userAccountDao = daoFactory.createUserAccountDao();
    private static UserDao userDao = daoFactory.createUserDao();
    private static final UserAccountService instance = new UserAccountService();

    private UserAccountService() {
        super(userAccountDao, userDao);
    }

    public static UserAccountService getInstance() {
        return instance;
    }
}

