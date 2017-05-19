package ua.timonov.web.project.dao.jdbc;

import org.apache.log4j.Logger;
import ua.timonov.web.project.model.location.Location;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.race.RaceStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RaceDao extends EntityDao {
    private static final Logger LOGGER = Logger.getLogger(HorseDao.class);

    private LocationDao locationDao = new LocationDao();

    public List<Race> getAll() {
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

    public Race getById(long raceId) {
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
    }

    private Race getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        RaceStatus raceStatus = RaceStatus.valueOf(transformToConstantView(resultSet.getString("status")));
        Location location = locationDao.getEntityFromResultSet(resultSet);
        Date date = resultSet.getDate("date");
        return new Race(id, raceStatus, location, date);
    }

    public Race getByHorseInRaceId(long horseInRaceId) {
        String sql = "SELECT race.id AS id, race_status.name AS status, location.name AS location, \n" +
                "location.id AS location_id, country.name AS country, country.id AS country_id, date\n" +
                "FROM RACE\n" +
                "INNER JOIN RACE_STATUS ON RACE.status_id = RACE_STATUS.ID\n" +
                "INNER JOIN LOCATION ON LOCATION_ID = LOCATION.ID\n" +
                "INNER JOIN COUNTRY ON LOCATION.COUNTRY_ID = COUNTRY.ID\n" +
                "INNER JOIN horse_in_race ON race.id = horse_in_race.race_id WHERE horse_in_race.id = ?";
        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, horseInRaceId);


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
    }

    public void save(Race race) {
        if (race.getId() == 0) {
            race.setId(create(race));
        } else {
            update(race);
        }
    }

    private long create(Race race) {
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
    }

    private void update(Race race) {
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
    }

    private void setEntityToParameters(Race race, PreparedStatement statement) throws SQLException {
        statement.setLong(1, race.getRaceStatus().ordinal() + 1);
        statement.setLong(2, race.getLocation().getId());
        // TODO consider Date problem
        statement.setDate(3, (java.sql.Date) race.getDate());
        if (statement.getParameterMetaData().getParameterCount() == 4) {
            statement.setLong(4, race.getId());
        }
    }
}
