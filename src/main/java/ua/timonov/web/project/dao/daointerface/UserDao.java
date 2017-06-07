package ua.timonov.web.project.dao.daointerface;

import ua.timonov.web.project.dao.Dao;
import ua.timonov.web.project.model.user.User;

/**
 * DAO interface for CRUD operations with entity User
 */
public interface UserDao extends Dao<User> {

    /**
     * finds user by its login
     * @param login     login
     * @return          found user
     */
    User findByLogin(String login);
}
