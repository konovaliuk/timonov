package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.JdbcDataManager;
import ua.timonov.web.project.dao.jdbc.HorseInRaceDao;
import ua.timonov.web.project.model.horse.HorseInRace;

import java.util.List;

public class HorseInRaceService {
    private static final Logger LOGGER = Logger.getLogger(HorseInRaceService.class);
    private static final JdbcDataManager dataManager = JdbcDataManager.getInstance();

    private HorseInRaceDao horseInRaceDao = new HorseInRaceDao();

    public List<HorseInRace> getAll() {
        return horseInRaceDao.getAll();
//        return horseDao.getAll().getResult();
    }

    public Object getByRace(long raceId) {
        return horseInRaceDao.getByRace(raceId);
    }
}
