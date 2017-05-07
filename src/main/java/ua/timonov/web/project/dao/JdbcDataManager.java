package ua.timonov.web.project.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.timonov.web.project.model.horse.Horse;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcDataManager {
    private static final Logger LOGGER = LogManager.getLogger(JdbcDataManager.class);
    private static final JdbcDataManager instance = new JdbcDataManager();

    private DataSource dataSource = DataSourceFactory.getInstance().getDataSource();

    private JdbcDataManager() {
    }

    public static JdbcDataManager getInstance() {
        return instance;
    }

    public QueryResult getAll() {
        String sql = "SELECT ID, NAME, YEAR, TOTALRACES, WONRACES FROM HORSE";
        LOGGER.info(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            LOGGER.info(ps.toString());
            List<Horse> result = new ArrayList<>();
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    result.add(getEntityFromResultSet(resultSet));
                }
            }
            return new QueryResult<>(result, result.size());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Database operation failed! " + e.getMessage());
        }
    }

    private Horse getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        int yearOfBirth = resultSet.getInt("year");
        int totalRaces = resultSet.getInt("totalraces");
        int wonRaces = resultSet.getInt("wonraces");
        return new Horse(id, name, yearOfBirth, totalRaces, wonRaces);
    }

}
