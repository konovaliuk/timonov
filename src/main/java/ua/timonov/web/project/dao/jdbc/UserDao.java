package ua.timonov.web.project.dao.jdbc;

import org.apache.log4j.Logger;
import ua.timonov.web.project.model.user.User;
import ua.timonov.web.project.model.user.UserAccount;
import ua.timonov.web.project.model.user.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao extends EntityDao {

    private static final Logger LOGGER = Logger.getLogger(UserDao.class);

    public User getById(long userId) {
        String sql = "SELECT user.id AS id, user_type.name AS role, login, password, user.name AS name, " +
                "user.account_id AS accountId, balance\n" +
                "FROM user INNER JOIN user_type ON user.type_id = user_type.id\n" +
                "INNER JOIN account ON user.account_id = account.id\n" +
                "WHERE user.id = ?";
        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, userId);

            LOGGER.info(ps.toString());
            User result = null;
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result = getEntityFromResultSet(resultSet);
                }
            }
            return result; // TODO
//            return new QueryResult<T>(result, result.size());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Database operation failed! " + e.getMessage());
        }
    }

    private User getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        UserType userType = UserType.valueOf(transformToConstantView(resultSet.getString("role")));
        String name = resultSet.getString("name");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        long accountId = resultSet.getLong("accountId");
        double balance = resultSet.getDouble("balance");
        return new User(id, userType, login, password, name, new UserAccount(accountId, balance));
    }
}
