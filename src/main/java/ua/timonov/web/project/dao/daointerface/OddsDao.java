package ua.timonov.web.project.dao.daointerface;

import ua.timonov.web.project.dao.Dao;
import ua.timonov.web.project.model.bet.Odds;

import java.util.List;

/**
 * DAO interface for CRUD operations with entity Odds
 */
public interface OddsDao extends Dao<Odds> {

    /**
     * fonds list of Odds by HorseInRace ID
     * @param horseInRaceId         HorseInRace ID
     * @return                      list of Odds
     */
    List<Odds> findListByHorseInRace(Long horseInRaceId);
}
