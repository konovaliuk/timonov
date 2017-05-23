package ua.timonov.web.project.dao;

import ua.timonov.web.project.dao.daointerface.*;
import ua.timonov.web.project.dao.jdbc.mysql.MysqlDaoFactory;
import ua.timonov.web.project.exception.AppException;

public abstract class DaoFactory {

    public abstract BetDao createBetDao();

    public abstract CountryDao createCountryDao();

    public abstract HorseDao createHorseDao();

    public abstract HorseInRaceDao createHorseInRaceDao();

    public abstract LocationDao createLocationDao();

    public abstract OddsDao createOddsDao();

    public abstract RaceDao createRaceDao();

    public abstract UserDao createUserDao();

    public abstract UserAccountDao createUserAccountDao();

    public static DaoFactory getFactory(DatabaseType databaseType) {
        switch (databaseType) {
            case MYSQL: return MysqlDaoFactory.getInstance();
            // TODO think twice
            case POSTGRES: throw new AppException("Support for Postgres is not implemented");
            default: return MysqlDaoFactory.getInstance();
        }
    }
}