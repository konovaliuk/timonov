package ua.timonov.web.project.dao.jdbc.mysql;

import ua.timonov.web.project.dao.daointerface.OddsDao;
import ua.timonov.web.project.dao.jdbc.EntityDao;
import ua.timonov.web.project.model.bet.BetType;
import ua.timonov.web.project.model.bet.Odds;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MysqlOddsDao extends EntityDao<Odds> implements OddsDao {

    public static final int HORSE_IN_RACE_ID_INDEX = 1;
    public static final int BET_TYPE_INDEX = 2;
    public static final int TOTAL_INDEX = 3;
    public static final int CHANCES_INDEX = 4;
    public static final int ID_INDEX = 5;
    public static final String FIND_BY_HORSE_IN_RACE = "findListByHorseInRace";
    public static final String ENTITY_NAME = "Odds";

    private static final MysqlOddsDao instance = new MysqlOddsDao();

    private MysqlOddsDao() {
        super(ENTITY_NAME);
    }

    public static MysqlOddsDao getInstance() {
        return instance;
    }

    @Override
    public List<Odds> findListByHorseInRace(long horseInRaceId) {
        String sql = getQuery(FIND_ALL) + " " + getQuery(FIND_BY_HORSE_IN_RACE);
        return findListWithSql(sql, horseInRaceId);
    }

    protected Odds getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("odds_id");
        long horseInRaceId = resultSet.getLong("horseInRace_id");
        BetType betType = BetType.valueOf(convertToEnumNameType(resultSet.getString("betName")));
        int total = resultSet.getInt("total");
        int chances = resultSet.getInt("chances");
        return new Odds(id, horseInRaceId, betType, total, chances);
    }

    @Override
    protected void setEntityToParameters(Odds odds, PreparedStatement statement)
            throws SQLException {
        statement.setLong(HORSE_IN_RACE_ID_INDEX, odds.getHorseInRaceId());
        statement.setInt(BET_TYPE_INDEX, odds.getBetType().ordinal() + 1);
        statement.setInt(TOTAL_INDEX, odds.getTotal());
        statement.setInt(CHANCES_INDEX, odds.getChances());
        if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
            statement.setLong(ID_INDEX, odds.getId());
        }
    }
}