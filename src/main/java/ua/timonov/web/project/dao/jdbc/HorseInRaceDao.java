package ua.timonov.web.project.dao.jdbc;

import org.apache.log4j.Logger;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.model.horse.HorseInRace;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HorseInRaceDao extends EntityDao {
    private static final Logger LOGGER = Logger.getLogger(HorseDao.class);

    public List<HorseInRace> getAll() {
        String sql = "SELECT HORSE_IN_RACE.ID AS ID, HORSE_ID, HORSE.NAME, HORSE.YEAR, HORSE.TOTALRACES, HORSE.WONRACES, PLACE\n" +
                "FROM HORSE_IN_RACE\n" +
                "INNER JOIN HORSE ON HORSE_IN_RACE.HORSE_ID = HORSE.ID";
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
    }

    private HorseInRace getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        Horse horse = getHorseFromResultSet(resultSet);
        int finishPlace = resultSet.getInt("place");
        return new HorseInRace(id, horse, finishPlace);
    }

    private Horse getHorseFromResultSet(ResultSet resultSet) throws SQLException {
        long horseId = resultSet.getLong("horse_id");
        String name = resultSet.getString("name");
        int yearOfBirth = resultSet.getInt("year");
        int totalRaces = resultSet.getInt("totalraces");
        int wonRaces = resultSet.getInt("wonraces");
        return new Horse(horseId, name, yearOfBirth, totalRaces, wonRaces);
    }

    public List<HorseInRace> getByRace(long raceId) {
        String sql = "SELECT HORSE_IN_RACE.ID AS ID, HORSE_ID, HORSE.NAME, HORSE.YEAR, HORSE.TOTALRACES, HORSE.WONRACES, PLACE\n" +
                "FROM HORSE_IN_RACE\n" +
                "INNER JOIN HORSE ON HORSE_IN_RACE.HORSE_ID = HORSE.ID WHERE HORSE_IN_RACE.RACE_ID = ?\n" +
                "ORDER BY PLACE, HORSE_ID";
        LOGGER.info(sql);
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

    public HorseInRace getById(long id) {
        String sql = "SELECT HORSE_IN_RACE.ID AS ID, HORSE_ID, HORSE.NAME, HORSE.YEAR, HORSE.TOTALRACES, HORSE.WONRACES, PLACE\n" +
                "FROM HORSE_IN_RACE\n" +
                "INNER JOIN HORSE ON HORSE_IN_RACE.HORSE_ID = HORSE.ID WHERE HORSE_IN_RACE.ID = ?";
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
    }

    public void save(long raceId, HorseInRace horseInRace) {
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
    }

    private void setEntityToParameters(long raceId, HorseInRace horseInRace, PreparedStatement statement) throws SQLException {
        statement.setLong(1, raceId);
        statement.setLong(2, horseInRace.getHorse().getId());
        statement.setInt(3, horseInRace.getFinishPlace());
        if (statement.getParameterMetaData().getParameterCount() == 4) {
            statement.setLong(4, horseInRace.getId());
        }
    }
}
