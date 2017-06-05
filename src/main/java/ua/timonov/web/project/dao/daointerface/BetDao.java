package ua.timonov.web.project.dao.daointerface;

import ua.timonov.web.project.dao.Dao;
import ua.timonov.web.project.model.bet.Bet;

import java.util.List;

public interface BetDao extends Dao<Bet> {

    List<Bet> findListByRaceId(long raceId);

    List<Bet> findListByUserId(Long userId);
}
