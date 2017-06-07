package ua.timonov.web.project.dao.jdbc.mysql;

import ua.timonov.web.project.dao.daointerface.HorseInRaceDao;
import ua.timonov.web.project.dao.jdbc.EntityDao;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.model.horse.HorseInRace;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MysqlHorseInRaceDao extends EntityDao<HorseInRace> implements HorseInRaceDao {

    public static final int RACE_ID_INDEX = 1;
    public static final int HORSE_ID_INDEX = 2;
    public static final int FINISH_PLACE_INDEX = 3;
    public static final int ID_INDEX = 4;
    public static final String FIND_LIST_BY_RACE_ID = "findListByRaceId";
    public static final String FIND_LIST_BY_HORSE_ID = "findListByHorseId";
    public static final String FIND_WITHOUT_ODDS = "findHorseInRaceWithoutOdds";

    public static final String ENTITY_NAME = "HorseInRace";
    private static final MysqlHorseInRaceDao instance = new MysqlHorseInRaceDao();

    private MysqlHorseInRaceDao() {
        super(ENTITY_NAME);
    }

    public static MysqlHorseInRaceDao getInstance() {
        return instance;
    }

    public List<HorseInRace> findListByRaceId(long raceId) {
        String sql = getQuery(FIND_ALL) + SPACE + getQuery(FIND_LIST_BY_RACE_ID);
        return findListWithSql(sql, raceId);
    }

    @Override
    public HorseInRace findHorseInRaceWithoutOdds(long id) {
        String sql = getQuery(FIND_ALL) + SPACE + getQuery(FIND_WITHOUT_ODDS);
        return findByIdWithSql(id, sql, "Race");
    }

    @Override
    public List<HorseInRace> findListByHorseId(long horseId) {
        String sql = getQuery(FIND_ALL) + SPACE + getQuery(FIND_LIST_BY_HORSE_ID);
        return findListWithSql(sql, horseId);
    }

    protected HorseInRace getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("horseInRace_id");
        long raceId = resultSet.getLong("race_id");
        int finishPlace = resultSet.getInt("place");
        Horse horse = getHorseFromResultSet(resultSet);
        return new HorseInRace(id, raceId, horse, finishPlace);
    }

    private Horse getHorseFromResultSet(ResultSet resultSet) throws SQLException {
        MysqlHorseDao mysqlHorseDao = MysqlHorseDao.getInstance();
        return mysqlHorseDao.getEntityFromResultSet(resultSet);
    }

    @Override
    protected void setEntityToParameters(HorseInRace horseInRace, PreparedStatement statement)
            throws SQLException {

        statement.setLong(RACE_ID_INDEX, horseInRace.getRaceId());
        statement.setLong(HORSE_ID_INDEX, horseInRace.getHorse().getId());
        statement.setInt(FINISH_PLACE_INDEX, horseInRace.getFinishPlace());
        if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
            statement.setLong(ID_INDEX, horseInRace.getId());
        }
    }

    @Override
    protected String getQuerySuffixOrderBy() {
        return QUERIES.getString(entityName + "." + ORDER_BY);
    }
}