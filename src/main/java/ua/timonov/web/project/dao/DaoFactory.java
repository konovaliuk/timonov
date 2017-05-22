package ua.timonov.web.project.dao;

import ua.timonov.web.project.dao.daointerface.*;

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
}
