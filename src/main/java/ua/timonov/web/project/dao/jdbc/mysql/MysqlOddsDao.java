package ua.timonov.web.project.dao.jdbc.mysql;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.daointerface.OddsDao;
import ua.timonov.web.project.dao.jdbc.EntityDao;
import ua.timonov.web.project.model.bet.BetType;
import ua.timonov.web.project.model.bet.Odds;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MysqlOddsDao extends EntityDao<Odds> implements OddsDao {

    public static final int HORSE_IN_RACE_ID_INDEX = 1;
    public static final int BET_TYPE_INDEX = 2;
    public static final int TOTAL_INDEX = 3;
    public static final int CHANCES_INDEX = 4;
    public static final int ID_INDEX = 5;
    public static final String FIND_BY_HORSE_IN_RACE = "findListByHorseInRace";
    public static final String ENTITY_NAME = "Odds";

    private static final Logger LOGGER = Logger.getLogger(MysqlOddsDao.class);
    private static final MysqlOddsDao instance = new MysqlOddsDao();

    private MysqlOddsDao() {
        super(ENTITY_NAME);
    }

    public static MysqlOddsDao getInstance() {
        return instance;
    }

    @Override
    public List<Odds> findListByHorseInRace(long horseInRaceId) {
        String sql = getQuery(FIND_ALL) + " " + getQuery(FIND_BY_HORSE_IN_RACE);
        return findListWithSql(sql, horseInRaceId);
    }

    protected Odds getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("odds_id");
        long horseInRaceId = resultSet.getLong("horseInRace_id");
        BetType betType = BetType.valueOf(convertToEnumNameType(resultSet.getString("betName")));
        int total = resultSet.getInt("total");
        int chances = resultSet.getInt("chances");
        return new Odds(id, horseInRaceId, betType, total, chances);
    }

    @Override
    protected void setEntityToParameters(Odds odds, PreparedStatement statement)
            throws SQLException {
        statement.setLong(HORSE_IN_RACE_ID_INDEX, odds.getHorseInRaceId());
        statement.setInt(BET_TYPE_INDEX, odds.getBetType().ordinal() + 1);
        statement.setInt(TOTAL_INDEX, odds.getTotal());
        statement.setInt(CHANCES_INDEX, odds.getChances());
        if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
            statement.setLong(ID_INDEX, odds.getId());
        }
    }
}


/*try (Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)) {

        ps.setLong(1, horseInRaceId);
        LOGGER.info(ps.toString());
        List<Odds> result = new ArrayList<>();
        try (ResultSet resultSet = ps.executeQuery()) {
        while (resultSet.next()) {
        result.add(getEntityFromResultSet(resultSet));
        }
        }
        LOGGER.info(result.size() + " records founded");
        return result;
//            return new QueryResult<>(result, result.size());
        } catch (SQLException e) {
        LOGGER.error("Database error while searching in table " + ENTITY_NAME +
        " by horseInRace id = " + horseInRaceId + ", exception message: " + e.getMessage());
        return null;
        }*/


/*@Override
    public boolean save(Odds odds) {
        return false;
    }

    @Override
    public boolean delete(Odds odds) {
        return false;
    }

    @Override
    public Odds findById(long id) {
        return null;
    }

    @Override
    public List<Odds> findAll() {
        return null;
    }*/

    /*public Odds findById(long oddsId) {
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
            return result;
//            return new QueryResult<T>(result, result.size());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Database operation failed! " + e.getMessage());
        }
    }*/

/*public void save(long horseInRaceId, Odds odds) {
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
    }*/

    /*public void delete(long oddsId) {
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
    }*/