package ua.timonov.web.project.dao.jdbc.mysql;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.daointerface.BetDao;
import ua.timonov.web.project.dao.jdbc.EntityDao;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.bet.BetType;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.user.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlBetDao extends EntityDao<Bet> implements BetDao {

    public static final int USER_INDEX = 1;
    public static final int BET_TYPE_INDEX = 2;
    public static final int HORSE_IN_RACE_INDEX = 3;
    public static final int SUM_INDEX = 4;
    public static final int ID_INDEX = 5;
    public static final String ENTITY_NAME = "bet";

    private static final Logger LOGGER = Logger.getLogger(MysqlBetDao.class);
    private static final MysqlBetDao instance = new MysqlBetDao();

    private MysqlBetDao() {
        super(ENTITY_NAME);
    }

    public static MysqlBetDao getInstance() {
        return instance;
    }

    @Override
    protected Bet getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");

        // TODO add JOIN to SELECT
        long userId = resultSet.getLong("user_id");
        User user = MysqlUserDao.getInstance().findById(userId);

        // TODO add JOIN to SELECT
        int betTypeId = resultSet.getInt("betType_id");
        BetType betType = BetType.values()[betTypeId];

        // TODO add JOIN to SELECT
        long horseInRaceId = resultSet.getLong("horse_in_race_id");
        HorseInRace horseInRace = MysqlHorseInRaceDao.getInstance().findById(horseInRaceId);

        double sum = resultSet.getDouble("sum");
        return new Bet(id, user, betType, horseInRace, sum);
    }

    protected void setEntityToParameters(Bet bet, PreparedStatement statement, long... externalId) throws SQLException {
        statement.setLong(USER_INDEX, bet.getUser().getId());
        statement.setLong(BET_TYPE_INDEX, bet.getBetType().ordinal() + 1);
        statement.setLong(HORSE_IN_RACE_INDEX, bet.getHorseInRace().getId());
        statement.setDouble(SUM_INDEX, bet.getSum());
        if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
            statement.setLong(ID_INDEX, bet.getId());
        }
    }
}


/*@Override
    public boolean save(Bet bet) {
        if (bet.getId() == 0) {
            bet.setId(create(bet));
        } else {
            update(bet);
        }
        return true;
    }*/

    /*@Override
    public boolean delete(Bet bet) {
        return false;
    }

    @Override
    public Bet findById(long id) {
        return null;
    }

    @Override
    public List<Bet> findAll() {
        return null;
    }*/

    /*private long create(Bet bet) {
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
    }*/

    /*private void update(Bet bet) {
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
    }*/