package ua.timonov.web.project.dao.jdbc.mysql;

import ua.timonov.web.project.dao.daointerface.BetDao;
import ua.timonov.web.project.dao.jdbc.EntityDao;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.bet.BetStatus;
import ua.timonov.web.project.model.bet.Odds;
import ua.timonov.web.project.model.user.User;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MysqlBetDao extends EntityDao<Bet> implements BetDao {

    public static final int USER_INDEX = 1;
    public static final int ODDS_INDEX = 2;
    public static final int SUM_INDEX = 3;
    public static final int STATUS_INDEX = 4;
    public static final int ID_INDEX = 5;
    public static final String ENTITY_NAME = "Bet";

    private static final MysqlBetDao instance = new MysqlBetDao();
    public static final String FIND_LIST_BY_RACE_ID = "findListByRaceId";
    public static final String FIND_LIST_BY_USER_ID = "findListByUserId";

    private MysqlBetDao() {
        super(ENTITY_NAME);
    }

    public static MysqlBetDao getInstance() {
        return instance;
    }

    @Override
    protected Bet getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        User user = MysqlUserDao.getInstance().getEntityFromResultSet(resultSet);
        Odds odds = MysqlOddsDao.getInstance().getEntityFromResultSet(resultSet);
        BigDecimal sum = resultSet.getBigDecimal("sum");
        BetStatus betStatus = BetStatus.valueOf(convertToEnumNameType(resultSet.getString("betStatus")));
        return new Bet.Builder(user, odds)
                .id(id)
                .money(sum)
                .betStatus(betStatus)
                .build();
    }

    @Override
    public List<Bet> findListByRaceId(long raceId) {
        String sql = getQuery(FIND_ALL) + " " + getQuery(FIND_LIST_BY_RACE_ID);
        return findListWithSql(sql, raceId);
    }

    @Override
    public List<Bet> findListByUserId(Long userId) {
        String sql = getQuery(FIND_ALL) + " " + getQuery(FIND_LIST_BY_USER_ID);
        return findListWithSql(sql, userId);
    }

    protected void setEntityToParameters(Bet bet, PreparedStatement statement) throws SQLException {
        statement.setLong(USER_INDEX, bet.getUser().getId());
        statement.setLong(ODDS_INDEX, bet.getOdds().getId());
        statement.setBigDecimal(SUM_INDEX, bet.getSum().getValue());
        statement.setLong(STATUS_INDEX, bet.getBetStatus().ordinal() + 1);
        if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
            statement.setLong(ID_INDEX, bet.getId());
        }
    }
}