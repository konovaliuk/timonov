package ua.timonov.web.project.dao.jdbc;

import org.apache.log4j.Logger;
import ua.timonov.web.project.model.bet.BetType;
import ua.timonov.web.project.model.bet.Odds;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OddsDao extends EntityDao {

    private static final Logger LOGGER = Logger.getLogger(OddsDao.class);

    public List<Odds> getByHorseInRace(long horseInRaceId) {
        String sql = "SELECT ODDS.ID AS ID, BET_TYPE.NAME AS BET_NAME, TOTAL, CHANCES\n" +
                "FROM ODDS\n" +
                "INNER JOIN BET_TYPE ON ODDS.BET_TYPE_ID = BET_TYPE.ID\n" +
                "WHERE odds.horse_in_race_id = ?\n" +
                "ORDER BY BET_TYPE_ID";
        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, horseInRaceId);
            LOGGER.info(ps.toString());
            List<Odds> result = new ArrayList<>();
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

    private Odds getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        BetType betType = BetType.valueOf(transformToConstantView(resultSet.getString("bet_name")));
        int total = resultSet.getInt("total");
        int chances = resultSet.getInt("chances");
        return new Odds(id, betType, total, chances);
    }

    public Odds getById(long oddsId) {
        String sql = "SELECT odds.id AS id, horse_in_race_id, bet_type.name AS bet_name, total, chances\n" +
                "FROM odds INNER JOIN bet_type ON odds.bet_type_id = bet_type.id\n" +
                "WHERE odds.id = ?";
        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, oddsId);

            LOGGER.info(ps.toString());
            Odds result = null;
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

    public void save(long horseInRaceId, Odds odds) {
        if (odds.getId() == 0) {
            odds.setId(create(horseInRaceId, odds));
        } else {
            update(horseInRaceId, odds);
        }
    }

    private long create(long horseInRaceId, Odds odds) {
        String sql = "INSERT INTO odds (horse_in_race_id, bet_type_id, total, chances)\n" +
                "VALUES (?, ?, ?, ?)";
        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setEntityToParameters(horseInRaceId, odds, statement);
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

    private void update(long horseInRaceId, Odds odds) {
        String sql = "UPDATE odds SET horse_in_race_id = ?, bet_type_id = ?, total = ?, chances = ?\n" +
                "WHERE id = ?";
        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setEntityToParameters(horseInRaceId, odds, statement);
            statement.execute();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Database operation failed! " + e.getMessage());
        }
    }

    private void setEntityToParameters(long horseInRaceId, Odds odds, PreparedStatement statement) throws SQLException {
        statement.setLong(1, horseInRaceId);
        statement.setLong(2, odds.getBetType().ordinal() + 1);
        statement.setLong(3, odds.getTotal());
        statement.setDouble(4, odds.getChances());
        if (statement.getParameterMetaData().getParameterCount() == 5) {
            statement.setLong(5, odds.getId());
        }
    }

    public void delete(long oddsId) {
        String sql = "DELETE FROM odds WHERE id = ?";
        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, oddsId);
            statement.execute();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Database operation failed! " + e.getMessage());
        }
    }
}
