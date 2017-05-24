package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.Dao;
import ua.timonov.web.project.dao.daointerface.HorseInRaceDao;
import ua.timonov.web.project.dao.daointerface.OddsDao;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.bet.Odds;
import ua.timonov.web.project.model.horse.HorseInRace;

import java.util.List;

public class HorseInRaceService extends DataService<HorseInRace> {

    private static final Logger LOGGER = Logger.getLogger(HorseInRaceService.class);

    private static HorseInRaceDao horseInRaceDao = daoFactory.createHorseInRaceDao();
    private static OddsDao oddsDao = daoFactory.createOddsDao();
    private static final HorseInRaceService instance = new HorseInRaceService(horseInRaceDao);

    private HorseInRaceService(Dao<HorseInRace> horseInRaceDao) {
        super(horseInRaceDao, "Horse in Race");
    }

    public static HorseInRaceService getInstance() {
        return instance;
    }

    @Override
    public HorseInRace findById(long id) {
        HorseInRace horseInRace = super.findById(id);
        List<Odds> oddsByHorseInRace = oddsDao.findByHorseInRace(horseInRace.getId());
        if (oddsByHorseInRace == null) {
            LOGGER.error("Items not founded in Odds");
            throw new ServiceException("Items not founded in Odds");
        }
        horseInRace.setOddsValues(oddsByHorseInRace);
        return horseInRace;
    }

    public List<HorseInRace> findByRaceId(long raceId) {
        List<HorseInRace> listFromDatabase = horseInRaceDao.findListByRaceId(raceId);
        for (HorseInRace horseInRace : listFromDatabase) {
            horseInRace.setOddsValues(oddsDao.findByHorseInRace(horseInRace.getId()));
        }
        return listFromDatabase;
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
