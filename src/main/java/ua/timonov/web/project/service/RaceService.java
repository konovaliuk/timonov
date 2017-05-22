package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.daointerface.RaceDao;
import ua.timonov.web.project.dao.jdbc.mysql.MysqlDaoFactory;
import ua.timonov.web.project.model.race.Race;

import java.util.List;

public class RaceService {
    private static final Logger LOGGER = Logger.getLogger(RaceService .class);
    private RaceDao raceDao = MysqlDaoFactory.getInstance().createRaceDao();

    public List<Race> findAll() {
        return raceDao.findAll();
    }

    public Race getById(long id) throws DataServiceException {
        Race race = raceDao.findById(id);
        if (race == null) {
            throw new DataServiceException("ID " + id + "does not exist in" + raceDao.getName());
        }
        return race;
    }

    public Race getByHorseInRaceId(long horseInRaceId) {
        return raceDao.findByHorseInRaceId(horseInRaceId);
    }

    public void save(Race race) throws DataServiceException {
        try {
            raceDao.save(race);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new DataServiceException("Race saving failed!", e);
        }
    }
}
