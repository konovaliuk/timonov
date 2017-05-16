package ua.timonov.web.project.dao.jdbc;

import org.apache.log4j.Logger;
import ua.timonov.web.project.model.bet.Bet;

import java.sql.*;

public class BetDao extends EntityDao {

    private static final Logger LOGGER = Logger.getLogger(HorseDao.class);

    public void save(Bet bet) {
        if (bet.getId() == 0) {
            bet.setId(create(bet));
        } else {
            update(bet);
        }
    }

    private long create(Bet bet) {
        String sql = "INSERT INTO bet (user_id, betType_id, horse_in_race_id, sum)\n" +
                     "VALUES (?, ?, ?, ?)";
        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setEntityToParameters(bet, statement);
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

    private void update(Bet bet) {
        String sql = "UPDATE bet SET user_id = ?, betType_id = ?, horse_in_race_id = ?, sum = ?" +
                     "WHERE id = ?";
        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setEntityToParameters(bet, statement);
            statement.execute();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Database operation failed! " + e.getMessage());
        }
    }

    private void setEntityToParameters(Bet bet, PreparedStatement statement) throws SQLException {
        statement.setLong(1, bet.getUser().getId());
        statement.setLong(2, bet.getBetType().ordinal() + 1);
        statement.setLong(3, bet.getHorseInRace().getId());
        statement.setDouble(4, bet.getSum());
        if (statement.getParameterMetaData().getParameterCount() == 5) {
            statement.setLong(5, bet.getId());
        }
    }
}
