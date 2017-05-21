package ua.timonov.web.project.dao.jdbc.mysql;

import ua.timonov.web.project.dao.*;
import ua.timonov.web.project.dao.daointerface.*;

public class MysqlDaoFactory extends DaoFactory {

    private static final MysqlDaoFactory mysqlDaoFactory = new MysqlDaoFactory();

    private IBetDao betDao = MysqlBetDao.getInstance();
    private ICountryDao countryDao = MysqlCountryDao.getInstance();
    private IHorseDao horseDao = MysqlHorseDao.getInstance();
    private IHorseInRaceDao horseInRaceDao = MysqlHorseInRaceDao.getInstance();
    private ILocationDao locationDao = MySqlLocationDao.getInstance();
    private IOddsDao oddsDao = MysqlOddsDao.getInstance();
    private IRaceDao raceDao = MysqlRaceDao.getInstance();
    private IUserDao userDao = MysqlUserDao.getInstance();
    private IUserAccountDao userAccountDao = MysqlUserAccountDao.getInstance();

    private MysqlDaoFactory() {
    }

    public static MysqlDaoFactory getInstance() {
        return mysqlDaoFactory;
    }

    @Override
    public IBetDao createBetDao() {
        return betDao;
    }

    @Override
    public ICountryDao createCountryDao() {
        return countryDao;
    }

    @Override
    public IHorseDao createHorseDao() {
        return horseDao;
    }

    @Override
    public IHorseInRaceDao createHorseInRaceDao() {
        return horseInRaceDao;
    }

    @Override
    public ILocationDao createLocationDao() {
        return locationDao;
    }

    @Override
    public IOddsDao createOddsDao() {
        return oddsDao;
    }

    @Override
    public IRaceDao createRaceDao() {
        return raceDao;
    }

    @Override
    public IUserDao createUserDao() {
        return userDao;
    }

    @Override
    public IUserAccountDao createUserAccountDao() {
        return null;
    }
}
