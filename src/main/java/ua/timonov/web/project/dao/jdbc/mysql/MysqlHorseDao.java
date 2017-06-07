package ua.timonov.web.project.dao.jdbc.mysql;

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
    public static final String ENTITY_NAME = "Horse";

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