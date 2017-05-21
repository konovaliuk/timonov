package ua.timonov.web.project.dao;

import ua.timonov.web.project.dao.daointerface.*;

public abstract class DaoFactory {

    public abstract IBetDao createBetDao();

    public abstract ICountryDao createCountryDao();

    public abstract IHorseDao createHorseDao();

    public abstract IHorseInRaceDao createHorseInRaceDao();

    public abstract ILocationDao createLocationDao();

    public abstract IOddsDao createOddsDao();

    public abstract IRaceDao createRaceDao();

    public abstract IUserDao createUserDao();

    public abstract IUserAccountDao createUserAccountDao();
}
