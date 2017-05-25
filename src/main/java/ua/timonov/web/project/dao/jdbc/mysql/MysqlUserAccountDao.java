package ua.timonov.web.project.dao.jdbc.mysql;

import ua.timonov.web.project.dao.daointerface.UserAccountDao;
import ua.timonov.web.project.dao.jdbc.EntityDao;
import ua.timonov.web.project.model.user.Account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlUserAccountDao extends EntityDao<Account> implements UserAccountDao {

    public static final int BALANCE_INDEX = 1;
    public static final int ID_INDEX = 2;
    public static final String ENTITY_NAME = "account";

    private static final MysqlUserAccountDao instance = new MysqlUserAccountDao();

    private MysqlUserAccountDao() {
        super(ENTITY_NAME);
    }

    public static MysqlUserAccountDao getInstance() {
        return instance;
    }

    @Override
    protected Account getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        double balance = resultSet.getDouble("balance");
        return new Account(id, balance);
    }

    @Override
    protected void setEntityToParameters(Account account, PreparedStatement statement)
            throws SQLException {

        statement.setBigDecimal(BALANCE_INDEX, account.getBalance().getValue());
        if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
            statement.setLong(ID_INDEX, account.getId());
        }
    }
}

/*@Override
    public boolean save(UserAccount userAccount) {
        return false;
    }

    @Override
    public boolean delete(UserAccount userAccount) {
        return false;
    }

    @Override
    public UserAccount findById(long id) {
        return null;
    }
*/
