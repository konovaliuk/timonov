package ua.timonov.web.project.dao.jdbc;

import org.apache.log4j.Logger;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.race.RaceStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RaceDao extends EntityDao {
    private static final Logger LOGGER = Logger.getLogger(HorseDao.class);

    public List<Race> getAll() {
        String sql = "SELECT RACE.ID AS ID, RACE_STATUS.NAME AS STATUS, LOCATION.NAME AS LOCATION, COUNTRY.NAME AS COUNTRY, DATE \n" +
                "FROM RACE\n" +
                "INNER JOIN RACE_STATUS ON RACE.status_id = RACE_STATUS.ID\n" +
                "INNER JOIN LOCATION ON LOCATION_ID = LOCATION.ID\n" +
                "INNER JOIN COUNTRY ON LOCATION.COUNTRY_ID = COUNTRY.ID";
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
        String sql = "SELECT RACE.ID AS ID, RACE_STATUS.NAME AS STATUS, LOCATION.NAME AS LOCATION, COUNTRY.NAME AS COUNTRY, DATE \n" +
                "FROM RACE\n" +
                "INNER JOIN RACE_STATUS ON RACE.status_id = RACE_STATUS.ID\n" +
                "INNER JOIN LOCATION ON LOCATION_ID = LOCATION.ID\n" +
                "INNER JOIN COUNTRY ON LOCATION.COUNTRY_ID = COUNTRY.ID WHERE RACE.ID = ?";
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
        String location = resultSet.getString("location");
        String country = resultSet.getString("country");
        Date date = resultSet.getDate("date");
        return new Race(id, raceStatus, location, country, date);
    }

    public Race getByHorseInRaceId(long horseInRaceId) {
        String sql = "SELECT RACE.ID AS ID, RACE_STATUS.NAME AS STATUS, LOCATION.NAME AS LOCATION, COUNTRY.NAME AS COUNTRY, DATE \n" +
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
}
