package ua.timonov.web.project.dao.jdbc.mysql;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.daointerface.UserDao;
import ua.timonov.web.project.dao.jdbc.EntityDao;
import ua.timonov.web.project.model.user.Client;
import ua.timonov.web.project.model.user.User;
import ua.timonov.web.project.model.user.Account;
import ua.timonov.web.project.model.user.UserType;

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
    public static final String ENTITY_NAME = "user";

    private static final Logger LOGGER = Logger.getLogger(MysqlUserDao.class);
    private static final MysqlUserDao instance = new MysqlUserDao();

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
            Account account = new Account(accountId, balance);
            return new Client(id, userType, login, password, name, account);
        } else {
            return new User(id, userType, login, password, name);
        }
    }

    @Override
    protected void setEntityToParameters(User user, PreparedStatement statement, long... externalId)
            throws SQLException {

        statement.setInt(USER_TYPE_INDEX, user.getUserType().ordinal() + 1);
        statement.setString(LOGIN_INDEX, user.getLogin());
        statement.setString(PASSWORD_INDEX, user.getPassword());
        statement.setString(NAME_INDEX, user.getName());
        if (user instanceof Client) {
            Client client = (Client) user;
            statement.setLong(ACCOUNT_ID_INDEX, client.getAccount().getId());
        }
        if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
            statement.setLong(ID_INDEX, user.getId());
        }
    }
}

/*@Override
    public boolean save(User user) {
        return false;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public User findById(long id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }
*/

/*public User findById(long userId) {
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
            return result;
//            return new QueryResult<T>(result, result.size());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Database operation failed! " + e.getMessage());
        }
    }*/
