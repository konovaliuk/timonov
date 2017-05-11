package ua.timonov.web.project.dao.jdbc;

import org.apache.log4j.Logger;
import ua.timonov.web.project.model.bet.BetType;
import ua.timonov.web.project.model.bet.Odds;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.model.horse.HorseInRace;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HorseInRaceDao extends EntityDao {
    private static final Logger LOGGER = Logger.getLogger(HorseDao.class);

    public List<HorseInRace> getAll() {
        String sql = "SELECT HORSE_IN_RACE.ID AS ID, HORSE_ID, HORSE.NAME, HORSE.YEAR, HORSE.TOTALRACES, HORSE.WONRACES, \n" +
                "BET_TYPE.NAME AS BET_NAME, HORSE_ODDS.ODDS_TOTAL, HORSE_ODDS.ODDS_CHANCES, PLACE\n" +
                " FROM HORSE_IN_RACE\n" +
                "INNER JOIN HORSE_ODDS ON HORSE_IN_RACE.ID = HORSE_ODDS.HORSE_IN_RACE_ID\n" +
                "INNER JOIN BET_TYPE ON HORSE_ODDS.BET_TYPE_ID = BET_TYPE.ID\n" +
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
        BetType betType = BetType.valueOf(transformToConstantView(resultSet.getString("bet_name")));
        int oddsTotal = resultSet.getInt("odds_total");
        int oddsChances = resultSet.getInt("odds_chances");
//        Map<BetType, Odds> horseOdds = new HashMap<>();
//        horseOdds.put(betType, new Odds(oddsTotal, oddsChances));
        return new HorseInRace(id, horse, finishPlace, betType, new Odds(oddsTotal, oddsChances));
    }

    private Horse getHorseFromResultSet(ResultSet resultSet) throws SQLException {
        Long horseId = resultSet.getLong("horse_id");
        String name = resultSet.getString("name");
        int yearOfBirth = resultSet.getInt("year");
        int totalRaces = resultSet.getInt("totalraces");
        int wonRaces = resultSet.getInt("wonraces");
        return new Horse(horseId, name, yearOfBirth, totalRaces, wonRaces);
    }

    public Object getByRace(long raceId) {
        String sql = "SELECT HORSE_IN_RACE.ID AS ID, HORSE_ID, HORSE.NAME, HORSE.YEAR, HORSE.TOTALRACES, HORSE.WONRACES, \n" +
                "BET_TYPE.NAME AS BET_NAME, HORSE_ODDS.ODDS_TOTAL, HORSE_ODDS.ODDS_CHANCES, PLACE\n" +
                " FROM HORSE_IN_RACE\n" +
                "INNER JOIN HORSE_ODDS ON HORSE_IN_RACE.ID = HORSE_ODDS.HORSE_IN_RACE_ID\n" +
                "INNER JOIN BET_TYPE ON HORSE_ODDS.BET_TYPE_ID = BET_TYPE.ID\n" +
                "INNER JOIN HORSE ON HORSE_IN_RACE.HORSE_ID = HORSE.ID WHERE HORSE_IN_RACE.RACE_ID = ?";
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
}
