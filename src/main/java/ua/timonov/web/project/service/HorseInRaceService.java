package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.JdbcDataManager;
import ua.timonov.web.project.dao.daointerface.HorseInRaceDao;
import ua.timonov.web.project.dao.daointerface.OddsDao;
import ua.timonov.web.project.dao.jdbc.mysql.MysqlDaoFactory;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.model.horse.HorseInRace;

import java.util.ArrayList;
import java.util.List;

public class HorseInRaceService {
    private static final Logger LOGGER = Logger.getLogger(HorseInRaceService.class);
    private static final JdbcDataManager dataManager = JdbcDataManager.getInstance();

    private HorseInRaceDao horseInRaceDao = MysqlDaoFactory.getInstance().createHorseInRaceDao();
    private OddsDao oddsDao = MysqlDaoFactory.getInstance().createOddsDao();

    public List<HorseInRace> findAll() {
        return horseInRaceDao.findAll();
    }

    public List<HorseInRace> getByRace(long raceId) {
        List<HorseInRace> listFromDatabase = horseInRaceDao.findListByRaceId(raceId);
        for (HorseInRace horseInRace : listFromDatabase) {
            horseInRace.setOddsValues(oddsDao.findByHorseInRace(horseInRace.getId()));
        }
        return listFromDatabase;
    }

    public HorseInRace getById(long id) {
        HorseInRace horseInRace = horseInRaceDao.findById(id);
        horseInRace.setOddsValues(oddsDao.findByHorseInRace(horseInRace.getId()));
        return horseInRace;
    }


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
    }

    public void save(HorseInRace horseInRace, long raceId) {
        try {
            horseInRaceDao.save(horseInRace, raceId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            // TODO - customize exception!
            throw new RuntimeException("HorseInRace saving failed!", e);
        }
    }
}
