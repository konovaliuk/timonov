package ua.timonov.web.project.dao.daointerface;

import ua.timonov.web.project.dao.Dao;
import ua.timonov.web.project.model.bet.Odds;

import java.util.List;

public interface OddsDao extends Dao<Odds> {

    List<Odds> findByHorseInRace(long horseInRaceId);
}
