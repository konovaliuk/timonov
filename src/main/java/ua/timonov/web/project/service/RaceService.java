package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.jdbc.RaceDao;
import ua.timonov.web.project.model.race.Race;

import java.util.List;

public class RaceService {
    private static final Logger LOGGER = Logger.getLogger(RaceService .class);
    private RaceDao raceDao = new RaceDao();

    public List<Race> getAll() {
        return raceDao.getAll();
    }

    public Race getById(long raceId) {
        return raceDao.getById(raceId);
    }

    public Race getByHorseInRaceId(long horseInRaceId) {
        return raceDao.getByHorseInRaceId(horseInRaceId);
    }
}
