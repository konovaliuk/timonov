package ua.timonov.web.project.service;

import ua.timonov.web.project.dao.Dao;
import ua.timonov.web.project.dao.daointerface.RaceDao;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.race.Race;

public class RaceService extends DataService<Race> {

    private static RaceDao raceDao = daoFactory.createRaceDao();
    private static final RaceService instance = new RaceService(raceDao);

    private RaceService(Dao<Race> raceDao) {
        super(raceDao, "Race");
    }

    public static RaceService getInstance() {
        return instance;
    }

    public Race getByHorseInRaceId(long horseInRaceId) {
        Race race = raceDao.findByHorseInRaceId(horseInRaceId);
        if (race == null) {
            throw new ServiceException("Horse in race " + horseInRaceId + " does not exist in table Race");
        }
        return race;
    }
}

    /*public List<Race> findAll() {
        return raceDao.findAll();
    }

    public Race findById(long id) throws ServiceException {
        Race race = raceDao.findById(id);
        if (race == null) {
            throw new ServiceException("ID " + id + "does not exist in " + raceDao.getName());
        }
        return race;
    }*/

    /*public void save(Race race) throws ServiceException {
        try {
            raceDao.save(race);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException("Race saving failed!", e);
        }
    }*/