package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.daointerface.HorseInRaceDao;
import ua.timonov.web.project.dao.daointerface.OddsDao;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.bet.Odds;
import ua.timonov.web.project.model.horse.HorseInRace;

import java.util.ArrayList;
import java.util.List;

public class HorseInRaceService extends DataService<HorseInRace, Odds> {

    private static final Logger LOGGER = Logger.getLogger(HorseInRaceService.class);

    private static HorseInRaceDao horseInRaceDao = daoFactory.createHorseInRaceDao();
    private static OddsDao oddsDao = daoFactory.createOddsDao();
    private static final HorseInRaceService instance = new HorseInRaceService();

    private HorseInRaceService() {
        super(horseInRaceDao, oddsDao);
    }

    public static HorseInRaceService getInstance() {
        return instance;
    }

    @Override
    public HorseInRace findById(long id) {
        HorseInRace horseInRace = super.findById(id);
        List<Odds> oddsByHorseInRace = oddsDao.findListByHorseInRace(horseInRace.getId());
        if (oddsByHorseInRace == null) {
            LOGGER.error("Items not founded in Odds");
            throw new ServiceException("Items not founded in Odds");
        }
        horseInRace.setOddsValues(oddsByHorseInRace);
        return horseInRace;
    }

    public List<HorseInRace> findListByRaceId(long raceId) {
        List<HorseInRace> listHorsesInRace = horseInRaceDao.findListByRaceId(raceId);
        for (HorseInRace horseInRace : listHorsesInRace) {
            horseInRace.setOddsValues(oddsDao.findListByHorseInRace(horseInRace.getId()));
        }
        return listHorsesInRace;
    }

    public void save(HorseInRace horseInRace) throws ServiceException {
        List<HorseInRace> listStoredHorsesInRace = horseInRaceDao.findListByRaceId(horseInRace.getRaceId());
        for (HorseInRace storedHorseInRace : listStoredHorsesInRace) {
            if (storedHorseInRace.equals(horseInRace)) {
                LOGGER.warn("Chosen horse is already in the race!");
                throw new ServiceException("Chosen horse is already in the race!");
            }
        }
        super.save(horseInRace);
    }

    public List<HorseInRace> findListByHorseId(long horseId) {
        List<HorseInRace> horsesInRace = horseInRaceDao.findListByHorseId(horseId);
        for (HorseInRace horseInRace : horsesInRace) {
            horseInRace.setOddsValues(oddsDao.findListByHorseInRace(horseInRace.getId()));
        }
        return horsesInRace;
    }

    public List<HorseInRace> findBetHorsesInRace(List<Bet> wonBets) {
        List<HorseInRace> listBetHorses = new ArrayList<>();
        for (Bet wonBet : wonBets) {
            long horseInRaceId = wonBet.getOdds().getHorseInRaceId();
            HorseInRace horseInRace = findById(horseInRaceId);
            listBetHorses.add(horseInRace);
        }
        return listBetHorses;
    }
}


/*
    @Deprecated
    private List<HorseInRace> unitEqualRecords(List<HorseInRace> listFromDatabase) {
        List<HorseInRace> result = new ArrayList<>();
        if (listFromDatabase.size() == 0) {
            return result;
        }
        // TODO with streams
        HorseInRace currentUnitedHorse = new HorseInRace();
        currentUnitedHorse.setHorse(listFromDatabase.get(0).getHorse());
        for (HorseInRace itemFromDatabase : listFromDatabase) {
            Horse currentHorse = itemFromDatabase.getHorse();
            if (currentUnitedHorse.getHorse().equals(currentHorse)) {
                currentUnitedHorse.getOddsValues().add(itemFromDatabase.getOddsValues().get(0));
            } else {
                result.add(currentUnitedHorse);
                currentUnitedHorse = itemFromDatabase;
            }
        }
        result.add(currentUnitedHorse);
        return result;
    }*/

    /*public void save(HorseInRace horseInRace, long raceId) {
        try {
            horseInRaceDao.save(horseInRace, raceId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            // TODO - customize exception!
            throw new ServiceException("HorseInRace saving failed!", e);
        }
    }*/
