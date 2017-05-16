package ua.timonov.web.project.dao.jdbc;

import org.apache.log4j.Logger;
import ua.timonov.web.project.model.horse.Horse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HorseDao extends EntityDao {

    private static final Logger LOGGER = Logger.getLogger(HorseDao.class);

    public List<Horse> getAll() {
        String sql = "SELECT ID, NAME, YEAR, TOTALRACES, WONRACES FROM HORSE";
//        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            LOGGER.info(ps.toString());
            List<Horse> result = new ArrayList<>();
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

    private Horse getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        int yearOfBirth = resultSet.getInt("year");
        int totalRaces = resultSet.getInt("totalraces");
        int wonRaces = resultSet.getInt("wonraces");
        return new Horse(id, name, yearOfBirth, totalRaces, wonRaces);
    }
}
