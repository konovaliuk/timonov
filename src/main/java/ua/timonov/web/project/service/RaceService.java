package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.jdbc.mysql.MysqlRaceDao;
import ua.timonov.web.project.model.race.Race;

import java.util.List;

public class RaceService {
    private static final Logger LOGGER = Logger.getLogger(RaceService .class);
    private MysqlRaceDao raceDao = new MysqlRaceDao();

    public List<Race> getAll() {
        // TODO
        return raceDao.getAll();
    }

    public Race getById(long raceId) {
        // TODO
        return raceDao.getById(raceId);
    }

    public Race getByHorseInRaceId(long horseInRaceId) {
        return raceDao.getByHorseInRaceId(horseInRaceId);
    }

    public void save(Race race) {
        try {
            raceDao.save(race);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            // TODO - customize exception!
            throw new RuntimeException("Race saving failed!", e);
        }
    }
}
