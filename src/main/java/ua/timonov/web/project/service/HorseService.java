package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.daointerface.HorseDao;
import ua.timonov.web.project.dao.daointerface.HorseInRaceDao;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.model.horse.HorseInRace;

public class HorseService extends DataService<Horse, HorseInRace> {

    private static final Logger LOGGER = Logger.getLogger(HorseService.class);
    private static HorseDao horseDao = daoFactory.createHorseDao();
    private static HorseInRaceDao horseInRaceDao = daoFactory.createHorseInRaceDao();
    private static final HorseService instance = new HorseService();

    private HorseService() {
        super(horseDao, horseInRaceDao);
    }

    public static HorseService getInstance() {
        return instance;
    }

    // TODO why custom method?
    public Horse findByHorseInRaceId(long horseInRaceId) {
        Horse horse = horseDao.findByForeignId(horseInRaceId, "HorseInRace");
        if (horse == null) {
            LOGGER.error("Horse in race " + horseInRaceId + " does not exist in table Horse");
            throw new ServiceException("Horse in race " + horseInRaceId + " does not exist in table Horse");
        }
        return horse;
    }

    public void validateNumberOfWonRaces(Horse horse) {
        if (horse.getTotalRaces() < horse.getWonRaces()) {
            LOGGER.error("Number of won races can't be more than total races");
            throw new ServiceException("Number of won races can't be more than total races");
        }
    }
}
