package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.DaoFactory;
import ua.timonov.web.project.dao.DatabaseType;
import ua.timonov.web.project.dao.daointerface.UserDao;
import ua.timonov.web.project.model.user.User;

public class UserService {

    private static final Logger LOGGER = Logger.getLogger(HorseService.class);
    private static final UserService instance = new UserService();

    private DaoFactory daoFactory = DaoFactory.getFactory(DatabaseType.MYSQL);
    private UserDao userDao = daoFactory.createUserDao();

    private UserService() {
    }

    public static UserService getInstance() {
        return instance;
    }

    public User getById(long userId) {
        return userDao.findById(userId);
    }
}
