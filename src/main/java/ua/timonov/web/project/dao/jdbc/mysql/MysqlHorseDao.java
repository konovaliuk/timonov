package ua.timonov.web.project.dao.jdbc.mysql;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.daointerface.HorseDao;
import ua.timonov.web.project.dao.jdbc.EntityDao;
import ua.timonov.web.project.model.horse.Horse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlHorseDao extends EntityDao<Horse> implements HorseDao {

    public static final int NAME_INDEX = 1;
    public static final int YEAR_INDEX = 2;
    public static final int TOTAL_RACES_INDEX = 3;
    public static final int WON_RACES_INDEX = 4;
    public static final int ID_INDEX = 5;
    public static final String ENTITY_NAME = "horse";

    private static final Logger LOGGER = Logger.getLogger(MysqlHorseDao.class);
    private static final MysqlHorseDao instance = new MysqlHorseDao();

    private MysqlHorseDao() {
        super(ENTITY_NAME);
    }

    public static MysqlHorseDao getInstance() {
        return instance;
    }

    protected Horse getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("horse_id");
        String name = resultSet.getString("horse_name");
        int yearOfBirth = resultSet.getInt("year");
        int totalRaces = resultSet.getInt("totalraces");
        int wonRaces = resultSet.getInt("wonraces");
        return new Horse(id, name, yearOfBirth, totalRaces, wonRaces);
    }

    @Override
    protected void setEntityToParameters(Horse horse, PreparedStatement statement, long... externalId)
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