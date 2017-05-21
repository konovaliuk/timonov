package ua.timonov.web.project.dao.jdbc.mysql;

import ua.timonov.web.project.dao.daointerface.IUserAccountDao;
import ua.timonov.web.project.dao.jdbc.EntityDao;
import ua.timonov.web.project.model.user.UserAccount;

import java.util.List;

public class MysqlUserAccountDao extends EntityDao<UserAccount> implements IUserAccountDao {

    private static final MysqlUserAccountDao instance = new MysqlUserAccountDao();

    private MysqlUserAccountDao() {
    }

    public static MysqlUserAccountDao getInstance() {
        return instance;
    }

    @Override
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

    @Override
    public List<UserAccount> findAll() {
        return null;
    }
}
