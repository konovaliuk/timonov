package ua.timonov.web.project.dao.jdbc.mysql;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.daointerface.HorseInRaceDao;
import ua.timonov.web.project.dao.jdbc.EntityDao;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.model.horse.HorseInRace;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlHorseInRaceDao extends EntityDao<HorseInRace> implements HorseInRaceDao {

    public static final int RACE_ID_INDEX = 1;
    public static final int HORSE_ID_INDEX = 2;
    public static final int FINISH_PLACE_INDEX = 3;
    public static final int ID_INDEX = 4;
    public static final String FIND_LIST_BY_RACE_ID = "findListByRaceId";
    public static final String ENTITY_NAME = "horseInRace";

    private static final Logger LOGGER = Logger.getLogger(MysqlHorseInRaceDao.class);
    private static final MysqlHorseInRaceDao instance = new MysqlHorseInRaceDao();


    private MysqlHorseInRaceDao() {
        super(ENTITY_NAME);
    }

    public static MysqlHorseInRaceDao getInstance() {
        return instance;
    }

    // TODO remove if not used
    public List<HorseInRace> findListByRaceId(long raceId) {
        String sql = getQuery(FIND_LIST_BY_RACE_ID);
//        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, raceId);
            LOGGER.info(ps.toString());
            List<HorseInRace> result = new ArrayList<>();
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

    protected HorseInRace getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
//        long raceId = resultSet.getLong("race_id");
        int finishPlace = resultSet.getInt("place");
        Horse horse = getHorseFromResultSet(resultSet);
        return new HorseInRace(id, horse, finishPlace);
    }

    private Horse getHorseFromResultSet(ResultSet resultSet) throws SQLException {
        MysqlHorseDao mysqlHorseDao = MysqlHorseDao.getInstance();
        return mysqlHorseDao.getEntityFromResultSet(resultSet);
    }

    @Override
    protected void setEntityToParameters(HorseInRace horseInRace, PreparedStatement statement, long... externalId)
            throws SQLException {

        long raceId = externalId[0];
        statement.setLong(RACE_ID_INDEX, raceId);
        statement.setLong(HORSE_ID_INDEX, horseInRace.getHorse().getId());
        statement.setInt(FINISH_PLACE_INDEX, horseInRace.getFinishPlace());
        if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
            statement.setLong(ID_INDEX, horseInRace.getId());
        }
    }
}

    /*@Override
    public boolean save(HorseInRace horseInRace) {
        return false;
    }

    @Override
    public boolean delete(HorseInRace horseInRace) {
        return false;
    }

    @Override
    public HorseInRace findById(long id) {
        return null;
    }

    @Override
    public List<HorseInRace> findAll() {
        return null;
    }*/

    /*public List<HorseInRace> findAll() {
        String sql = "SELECT horse_in_race.id AS id, horse_id, horse.name, horse.year, horse.totalraces, " +
                "horse.wonraces, place\n" +
                "FROM horse_in_race\n" +
                "INNER JOIN horse ON horse_in_race.horse_id = horse.id";
//        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            LOGGER.info(ps.toString());
            List<HorseInRace> result = new ArrayList<>();
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
    }*/

    /*public HorseInRace findById(long id) {
        String sql = "SELECT horse_in_race.id AS id, horse_id, horse.name, horse.year, horse.totalraces, horse.wonraces, place\n" +
                "FROM horse_in_race\n" +
                "INNER JOIN horse ON horse_in_race.horse_id = horse.id WHERE horse_in_race.id = ?";
        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            LOGGER.info(ps.toString());
            HorseInRace result = null;
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result = getEntityFromResultSet(resultSet);
                }
            }
            return result;
//            return new QueryResult<>(result, result.size());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Database operation failed! " + e.getMessage());
        }
    }*/

    /*public void save(long raceId, HorseInRace horseInRace) {
        if (horseInRace.getId() == 0) {
            horseInRace.setId(create(raceId, horseInRace));
        } else {
            update(raceId, horseInRace);
        }
    }

    private long create(long raceId, HorseInRace horseInRace) {
        String sql = "INSERT INTO horse_in_race (race_id, horse_id, place)\n" +
                "VALUES (?, ?, ?)";
        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setEntityToParameters(raceId, horseInRace, statement);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                generatedKeys.next();
                return generatedKeys.getLong(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Database operation failed! " + e.getMessage());
        }
    }

    private void update(long raceId, HorseInRace horseInRace) {
        String sql = "UPDATE horse_in_race SET race_id = ?, horse_id = ?, place = ? WHERE id = ?";
        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setEntityToParameters(raceId, horseInRace, statement);
            statement.execute();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Database operation failed! " + e.getMessage());
        }
    }*/