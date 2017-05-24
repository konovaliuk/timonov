package ua.timonov.web.project.service;

import ua.timonov.web.project.dao.Dao;
import ua.timonov.web.project.dao.daointerface.UserDao;
import ua.timonov.web.project.model.user.User;

public class UserService extends DataService<User> {

    private static UserDao userDao = daoFactory.createUserDao();
    private static final UserService instance = new UserService(userDao);

    private UserService(Dao<User> raceDao) {
        super(raceDao, "User");
    }

    public static UserService getInstance() {
        return instance;
    }
}
