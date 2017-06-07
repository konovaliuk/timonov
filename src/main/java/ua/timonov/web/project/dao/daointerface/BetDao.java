package ua.timonov.web.project.dao.daointerface;

import ua.timonov.web.project.dao.Dao;
import ua.timonov.web.project.model.bet.Bet;

import java.util.List;

/**
 * DAO interface for CRUD operations with entity Bet
 */
public interface BetDao extends Dao<Bet> {

    /**
     * finds list of Bet by race ID
     * @param raceId        race ID
     * @return              List of Bet
     */
    List<Bet> findListByRaceId(Long raceId);

    /**
     * finds list of Bet by user ID
     * @param userId        user ID
     * @return              List of Bet
     */
    List<Bet> findListByUserId(Long userId);
}
