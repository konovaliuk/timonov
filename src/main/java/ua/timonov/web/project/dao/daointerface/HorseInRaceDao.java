package ua.timonov.web.project.dao.daointerface;

import ua.timonov.web.project.dao.Dao;
import ua.timonov.web.project.model.horse.HorseInRace;

import java.util.List;

/**
 * DAO interface for CRUD operations with entity HorseInRace
 */
public interface HorseInRaceDao extends Dao<HorseInRace> {

    /**
     * finds list of HorseInRace by race ID
     * @param raceId        race ID
     * @return              list of HorseInRace
     */
    List<HorseInRace> findListByRaceId(Long raceId);

    /**
     * finds HorseInRace without set bet odds by race ID
     * @param raceId        race ID
     * @return              found HorseInRace
     */
    HorseInRace findHorseInRaceWithoutOdds(Long raceId);

    /**
     * finds list of HorseInRace by horse ID
     * @param horseId        horse ID
     * @return               list of HorseInRace
     */
    List<HorseInRace> findListByHorseId(Long horseId);
}
