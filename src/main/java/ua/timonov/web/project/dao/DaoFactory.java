package ua.timonov.web.project.dao;

import ua.timonov.web.project.dao.daointerface.*;
import ua.timonov.web.project.dao.jdbc.mysql.MysqlDaoFactory;

/**
 * Abstract factory for application DAO
 */
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
            case MYSQL:
                return MysqlDaoFactory.getInstance();
            case POSTGRES:
                // not implemented, so go to default
            default:
                return MysqlDaoFactory.getInstance();
        }
    }
}