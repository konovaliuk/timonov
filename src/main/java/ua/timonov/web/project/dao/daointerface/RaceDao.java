package ua.timonov.web.project.dao.daointerface;

import ua.timonov.web.project.dao.Dao;
import ua.timonov.web.project.model.race.Race;

public interface RaceDao extends Dao<Race> {

    @Deprecated
    Race findByHorseInRaceId(long horseInRaceId);
}
