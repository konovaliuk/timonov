package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.jdbc.UserDao;
import ua.timonov.web.project.model.user.User;

public class UserService {

    private static final Logger LOGGER = Logger.getLogger(HorseService.class);

    private UserDao userDao = new UserDao();

    public User getById(long userId) {
        return userDao.getById(userId);
    }
}