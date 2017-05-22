package ua.timonov.web.project.dao.jdbc.mysql;

import ua.timonov.web.project.dao.*;
import ua.timonov.web.project.dao.daointerface.*;

public class MysqlDaoFactory extends DaoFactory {

    private static final MysqlDaoFactory mysqlDaoFactory = new MysqlDaoFactory();

    private BetDao betDao = MysqlBetDao.getInstance();
    private CountryDao countryDao = MysqlCountryDao.getInstance();
    private HorseDao horseDao = MysqlHorseDao.getInstance();
    private HorseInRaceDao horseInRaceDao = MysqlHorseInRaceDao.getInstance();
    private LocationDao locationDao = MySqlLocationDao.getInstance();
    private OddsDao oddsDao = MysqlOddsDao.getInstance();
    private RaceDao raceDao = MysqlRaceDao.getInstance();
    private UserDao userDao = MysqlUserDao.getInstance();
    private UserAccountDao userAccountDao = MysqlUserAccountDao.getInstance();

    private MysqlDaoFactory() {
    }

    public static MysqlDaoFactory getInstance() {
        return mysqlDaoFactory;
    }

    @Override
    public BetDao createBetDao() {
        return betDao;
    }

    @Override
    public CountryDao createCountryDao() {
        return countryDao;
    }

    @Override
    public HorseDao createHorseDao() {
        return horseDao;
    }

    @Override
    public HorseInRaceDao createHorseInRaceDao() {
        return horseInRaceDao;
    }

    @Override
    public LocationDao createLocationDao() {
        return locationDao;
    }

    @Override
    public OddsDao createOddsDao() {
        return oddsDao;
    }

    @Override
    public RaceDao createRaceDao() {
        return raceDao;
    }

    @Override
    public UserDao createUserDao() {
        return userDao;
    }

    @Override
    public UserAccountDao createUserAccountDao() {
        return null;
    }
}
