package ua.timonov.web.project.dao.jdbc.mysql;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.daointerface.UserDao;
import ua.timonov.web.project.dao.jdbc.EntityDao;
import ua.timonov.web.project.model.user.Money;
import ua.timonov.web.project.model.user.User;
import ua.timonov.web.project.model.user.Account;
import ua.timonov.web.project.model.user.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlUserDao extends EntityDao<User> implements UserDao {

    public static final int USER_TYPE_INDEX = 1;
    public static final int ACCOUNT_ID_INDEX = 2;
    public static final int LOGIN_INDEX = 3;
    public static final int PASSWORD_INDEX = 4;
    public static final int NAME_INDEX = 5;
    public static final int ID_INDEX = 6;
    public static final String ENTITY_NAME = "User";

    private static final Logger LOGGER = Logger.getLogger(MysqlUserDao.class);
    private static final MysqlUserDao instance = new MysqlUserDao();
    public static final String FIND_BY_LOGIN = "findByLogin";

    private MysqlUserDao() {
        super(ENTITY_NAME);
    }

    public static MysqlUserDao getInstance() {
        return instance;
    }

    protected User getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("user_id");
        UserType userType = UserType.valueOf(convertToEnumNameType(resultSet.getString("role")));
        String name = resultSet.getString("name");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        if (userType == UserType.CLIENT) {
            long accountId = resultSet.getLong("account_id");
            double balance = resultSet.getDouble("balance");
            Account account = new Account(accountId, new Money(balance));
            return new User(id, userType, login, password, name, account);
        } else {
            return new User(id, userType, login, password, name, null);
        }
    }

    @Override
    public User findByLogin(String login) {
        String sql = getQuery(FIND_ALL) + " " + getQuery(FIND_BY_LOGIN);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, login);
            LOGGER.info(statement.toString());
            User result = null;
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = getEntityFromResultSet(resultSet);
                }
            }
            if (result != null) {
                LOGGER.info("User with login = " + login + " found in table " + entityName);
            } else {
                LOGGER.info("User with login = " + login + " not found in table " + entityName + entityName);
            }
            return result;
        } catch (SQLException e) {
            LOGGER.error("Database error while searching in table " + entityName + " record with string = " + login +
                    ", exception message: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected void setEntityToParameters(User user, PreparedStatement statement)
            throws SQLException {

        statement.setInt(USER_TYPE_INDEX, user.getUserType().ordinal() + 1);
        statement.setString(LOGIN_INDEX, user.getLogin());
        statement.setString(PASSWORD_INDEX, user.getPassword());
        statement.setString(NAME_INDEX, user.getName());
        if (user.getUserType() == UserType.CLIENT) {
            statement.setLong(ACCOUNT_ID_INDEX, user.getAccount().getId());
        } else {
            statement.setString(ACCOUNT_ID_INDEX, null);
        }
        if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
            statement.setLong(ID_INDEX, user.getId());
        }
    }
}