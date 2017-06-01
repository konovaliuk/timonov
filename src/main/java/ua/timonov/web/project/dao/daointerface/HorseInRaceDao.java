package ua.timonov.web.project.dao.daointerface;

import ua.timonov.web.project.dao.Dao;
import ua.timonov.web.project.model.horse.HorseInRace;

import java.util.List;

public interface HorseInRaceDao extends Dao<HorseInRace> {

    List<HorseInRace> findListByRaceId(long raceId);

    HorseInRace findHorseInRaceWithoutOdds(long id);

    List<HorseInRace> findListByHorseId(long horseId);
}
