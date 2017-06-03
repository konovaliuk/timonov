package ua.timonov.web.project.dao.jdbc.mysql;

import org.apache.log4j.Logger;
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

public class MysqlRaceDao extends EntityDao<Race> implements RaceDao {

    public static final int RACE_STATUS_INDEX = 1;
    public static final int LOCATION_INDEX = 2;
    public static final int DATE_INDEX = 3;
    public static final int BET_SUM_INDEX = 4;
    public static final int PAID_SUM_INDEX = 5;
    public static final int ID_INDEX = 6;
    public static final String ENTITY_NAME = "Race";
    public static final String FIND_BY_HORSE_IN_RACE_ID = "findByHorseInRaceId";

    private static final MysqlRaceDao instance = new MysqlRaceDao();
    private static final Logger LOGGER = Logger.getLogger(MysqlRaceDao.class);

    private MysqlRaceDao() {
        super(ENTITY_NAME);
    }

    public static MysqlRaceDao getInstance() {
        return instance;
    }

    @Override
    @Deprecated
    public Race findByHorseInRaceId(long horseInRaceId) {
        String sql = getQuery(FIND_BY_HORSE_IN_RACE_ID);
        return findByForeignId(horseInRaceId, "HorseInRace");
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
        // TODO consider Date problem
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


/*try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, horseInRaceId);
            LOGGER.info(statement.toString());
            Race result = null;
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = getEntityFromResultSet(resultSet);
                }
            }
            if (result != null) {
                LOGGER.info("Race founded by horse in race with id = " + horseInRaceId);
            } else {
                LOGGER.info("Race not founded by horse in race with id = " + horseInRaceId);
            }
            return result;
            // TODO
//            return new QueryResult<T>(result, result.size());
        } catch (SQLException e) {
            LOGGER.error("Database error while searching in table " + ENTITY_NAME + " by horseInRace id " +
                    horseInRaceId + ", exception message: " + e.getMessage());
            return null;
        }*/


/*public List<Race> findAll() {
        String sql = "SELECT race.id AS id, race_status.name AS status, location.name AS location, " +
                "location.id AS location_id, country.name AS country, country.id AS country_id, date\n" +
                "FROM race\n" +
                "INNER JOIN race_status ON race.status_id = race_status.id\n" +
                "INNER JOIN location ON race.location_id = location.id\n" +
                "INNER JOIN country ON location.country_id = country.id";
//        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            LOGGER.info(ps.toString());
            List<Race> result = new ArrayList<>();
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    result.add(getEntityFromResultSet(resultSet));
                }
            }
            return result;
//            return new QueryResult<>(result, result.size());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Database operation failed! " + e.getMessage());
        }
    }

    public Race findById(long raceId) {
        String sql = "SELECT race.id AS id, race_status.name AS status, location.name AS location, \n" +
                "location.id AS location_id, country.name AS country, country.id AS country_id, date\n" +
                "FROM race\n" +
                "INNER JOIN race_status ON race.status_id = race_status.id\n" +
                "INNER JOIN location ON race.location_id = location.id\n" +
                "INNER JOIN country ON location.country_id = country.id WHERE RACE.ID = ?";
        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, raceId);

            LOGGER.info(ps.toString());
            Race result = null;
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
    }*/

/*public boolean save(Race race) {
        if (race.getId() == 0) {
            long raceId = create(race);
            race.setId(raceId);
            // TODO
            return raceId != 0;
        } else {
            return update(race);
        }
    }*/

    /*@Override
    public boolean delete(Race race) {
        return false;
    }

    @Override
    public Race findById(long id) {
        return null;
    }

    @Override
    public List<Race> findAll() {
        return null;
    }*/

    /*private long create(Race race) {
        String sql = "INSERT INTO race (status_id, location_id, date)\n" +
                "VALUES (?, ?, ?)";
        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setEntityToParameters(race, statement);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                generatedKeys.next();
                return generatedKeys.getLong(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Database operation failed! " + e.getMessage());
        }
    }*/

    /*private boolean update(Race race) {
        String sql = "UPDATE race SET status_id = ?, location_id = ?, date = ? WHERE id = ?";
        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setEntityToParameters(race, statement);
            statement.execute();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Database operation failed! " + e.getMessage());
        }
        // TODO
        return true;
    }*/