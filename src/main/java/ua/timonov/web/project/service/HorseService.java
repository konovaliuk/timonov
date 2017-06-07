package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.daointerface.HorseDao;
import ua.timonov.web.project.dao.daointerface.HorseInRaceDao;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.util.ExceptionMessages;

/**
 * Represents service for interact with DAO layer interface HorseDao and perform some logic with other DAO
 */
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

    /**
     * returns horse by HorseInRace ID
     * @param horseInRaceId
     * @return
     * @throws ServiceException
     */
    public Horse findByHorseInRaceId(Long horseInRaceId) throws ServiceException {
        Horse horse = horseDao.findByForeignId(horseInRaceId, "HorseInRace");
        if (horse == null) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.HORSE_IN_RACE_ID + " " + horseInRaceId +
                    " " + ExceptionMessages.HORSE_IN_RACE_NOT_FOUND);
            LOGGER.error(message);
            throw new ServiceException(message);
        }
        return horse;
    }

    /**
     * checks if user inputted value of won races more than total races for new horse
     * @param horse
     * @throws ServiceException
     */
    public void validateNumberOfWonRaces(Horse horse) throws ServiceException {
        if (horse.getTotalRaces() < horse.getWonRaces()) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.WON_RACES_MORE_TOTAL);
            LOGGER.error(message);
            throw new ServiceException(message);
        }
    }
}
