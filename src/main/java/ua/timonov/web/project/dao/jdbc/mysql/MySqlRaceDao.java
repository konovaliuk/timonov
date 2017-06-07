package ua.timonov.web.project.dao.jdbc.mysql;

import ua.timonov.web.project.dao.daointerface.RaceDao;
import ua.timonov.web.project.dao.jdbc.EntityDao;
import ua.timonov.web.project.model.location.Location;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.race.RaceStatus;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * MySql implementation for RaceDao interface
 */
public class MysqlRaceDao extends EntityDao<Race> implements RaceDao {

    public static final int RACE_STATUS_INDEX = 1;
    public static final int LOCATION_INDEX = 2;
    public static final int DATE_INDEX = 3;
    public static final int BET_SUM_INDEX = 4;
    public static final int PAID_SUM_INDEX = 5;
    public static final int ID_INDEX = 6;
    public static final String ENTITY_NAME = "Race";

    private static final MysqlRaceDao instance = new MysqlRaceDao();

    private MysqlRaceDao() {
        super(ENTITY_NAME);
    }

    public static MysqlRaceDao getInstance() {
        return instance;
    }

    protected Race getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        RaceStatus raceStatus = RaceStatus.valueOf(convertToEnumNameType(resultSet.getString("status")));
        Location location = getLocationFromResultSet(resultSet);
        Date date = resultSet.getDate("date");
        BigDecimal betSum = resultSet.getBigDecimal("betSum");
        BigDecimal paidSum = resultSet.getBigDecimal("paidSum");
        return new Race.Builder(location, date)
                .id(id)
                .raceStatus(raceStatus)
                .betSum(betSum)
                .paidSum(paidSum)
                .build();
    }

    private Location getLocationFromResultSet(ResultSet resultSet) throws SQLException {
        MysqlLocationDao mySqlLocationDao = MysqlLocationDao.getInstance();
        return mySqlLocationDao.getEntityFromResultSet(resultSet);
    }

    protected void setEntityToParameters(Race race, PreparedStatement statement) throws SQLException {
        statement.setLong(RACE_STATUS_INDEX, race.getRaceStatus().ordinal() + 1);
        statement.setLong(LOCATION_INDEX, race.getLocation().getId());
        statement.setDate(DATE_INDEX, new java.sql.Date(race.getDate().getTime()));
        statement.setBigDecimal(BET_SUM_INDEX, race.getBetSum().getValue());
        statement.setBigDecimal(PAID_SUM_INDEX, race.getPaidSum().getValue());
        if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
            statement.setLong(ID_INDEX, race.getId());
        }
    }

    protected String getQuerySuffixOrderBy() {
        return QUERIES.getString(entityName + "." + ORDER_BY);
    }
}