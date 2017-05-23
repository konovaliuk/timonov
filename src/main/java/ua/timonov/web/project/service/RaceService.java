package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.DaoFactory;
import ua.timonov.web.project.dao.DatabaseType;
import ua.timonov.web.project.dao.daointerface.RaceDao;
import ua.timonov.web.project.exception.ServiceLayerException;
import ua.timonov.web.project.model.race.Race;

import java.util.List;

public class RaceService {
    private static final Logger LOGGER = Logger.getLogger(RaceService .class);
    private static final RaceService instance = new RaceService();

    private DaoFactory daoFactory = DaoFactory.getFactory(DatabaseType.MYSQL);
    private RaceDao raceDao = daoFactory.createRaceDao();

    private RaceService() {
    }

    public static RaceService getInstance() {
        return instance;
    }

    public List<Race> findAll() {
        return raceDao.findAll();
    }

    public Race getById(long id) throws ServiceLayerException {
        Race race = raceDao.findById(id);
        if (race == null) {
            throw new ServiceLayerException("ID " + id + "does not exist in " + raceDao.getName());
        }
        return race;
    }

    public Race getByHorseInRaceId(long horseInRaceId) {
        Race race = raceDao.findByHorseInRaceId(horseInRaceId);
        if (race == null) {
            throw new ServiceLayerException("Horse in race " + horseInRaceId + " does not exist in " +
                    raceDao.getName());
        }
        return race;
    }

    public void save(Race race) throws ServiceLayerException {
        try {
            raceDao.save(race);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ServiceLayerException("Race saving failed!", e);
        }
    }
}
