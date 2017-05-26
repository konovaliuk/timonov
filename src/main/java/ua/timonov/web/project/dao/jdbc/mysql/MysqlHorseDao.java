package ua.timonov.web.project.dao.jdbc.mysql;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.daointerface.HorseDao;
import ua.timonov.web.project.dao.jdbc.EntityDao;
import ua.timonov.web.project.model.horse.Horse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlHorseDao extends EntityDao<Horse> implements HorseDao {

    public static final int NAME_INDEX = 1;
    public static final int YEAR_INDEX = 2;
    public static final int TOTAL_RACES_INDEX = 3;
    public static final int WON_RACES_INDEX = 4;
    public static final int ID_INDEX = 5;
    public static final String ENTITY_NAME = "Horse";
    public static final String FIND_BY_HORSE_IN_RACE_ID = "findByHorseInRaceId";

    private static final Logger LOGGER = Logger.getLogger(MysqlHorseDao.class);
    private static final MysqlHorseDao instance = new MysqlHorseDao();

    private MysqlHorseDao() {
        super(ENTITY_NAME);
    }

    public static MysqlHorseDao getInstance() {
        return instance;
    }

    @Override
    public Horse findByHorseInRaceId(long horseInRaceId) {
        String sql = getQuery(FIND_BY_HORSE_IN_RACE_ID);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, horseInRaceId);
            LOGGER.info(statement.toString());
            Horse result = null;
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = getEntityFromResultSet(resultSet);
                }
            }
            if (result != null) {
                LOGGER.info("Horse founded by HorseInRace with id = " + horseInRaceId);
            } else {
                LOGGER.info("Horse not founded by HorseInRace with id = " + horseInRaceId);
            }
            return result;
            // TODO
//            return new QueryResult<T>(result, result.size());
        } catch (SQLException e) {
            LOGGER.error("Database error while searching in table " + ENTITY_NAME + " by horseInRace id " +
                    horseInRaceId + ", exception message: " + e.getMessage());
            return null;
        }
    }

    protected Horse getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("horse_id");
        String name = resultSet.getString("horse_name");
        int yearOfBirth = resultSet.getInt("year");
        int totalRaces = resultSet.getInt("totalRaces");
        int wonRaces = resultSet.getInt("wonRaces");
        return new Horse(id, name, yearOfBirth, totalRaces, wonRaces);
    }

    @Override
    protected void setEntityToParameters(Horse horse, PreparedStatement statement)
            throws SQLException {

        statement.setString(NAME_INDEX, horse.getName());
        statement.setLong(YEAR_INDEX, horse.getYearOfBirth());
        statement.setInt(TOTAL_RACES_INDEX, horse.getTotalRaces());
        statement.setInt(WON_RACES_INDEX, horse.getWonRaces());
        if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
            statement.setLong(ID_INDEX, horse.getId());
        }
    }
}

/*@Override
    public boolean save(Horse horse) {
        return false;
    }

    @Override
    public boolean delete(Horse horse) {
        return false;
    }

    @Override
    public Horse findById(long id) {
        return null;
    }

    @Override
    public List<Horse> findAll() {
        return null;
    }*/

    /*public List<Horse> findAll() {
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
    }*/