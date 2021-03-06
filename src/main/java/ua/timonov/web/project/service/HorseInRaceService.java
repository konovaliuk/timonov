package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.daointerface.HorseInRaceDao;
import ua.timonov.web.project.dao.daointerface.OddsDao;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.bet.Odds;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.util.ExceptionMessages;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents service for interact with DAO layer interface HorseInRaceDao and perform some logic with other DAO
 */
public class HorseInRaceService extends DataService<HorseInRace, Odds> {

    private static final Logger LOGGER = Logger.getLogger(HorseInRaceService.class);

    private static HorseInRaceDao horseInRaceDao = daoFactory.createHorseInRaceDao();
    private static OddsDao oddsDao = daoFactory.createOddsDao();
    private static final HorseInRaceService instance = new HorseInRaceService();

    private HorseInRaceService() {
        super(horseInRaceDao, oddsDao);
    }

    public static HorseInRaceService getInstance() {
        return instance;
    }

    @Override
    public HorseInRace findById(Long id) throws ServiceException {
        HorseInRace horseInRace = super.findById(id);
        List<Odds> oddsByHorseInRace = oddsDao.findListByHorseInRace(horseInRace.getId());
        if (oddsByHorseInRace == null) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.FIND_ITEMS_FAILED + " " + oddsDao.getName());
            LOGGER.error(message);
            throw new ServiceException(message);
        }
        horseInRace.setOddsValues(oddsByHorseInRace);
        return horseInRace;
    }

    /**
     * finds list of HorseInRace in certain race by ID
     * @param raceId
     * @return
     */
    public List<HorseInRace> findListByRaceId(Long raceId) {
        List<HorseInRace> listHorsesInRace = horseInRaceDao.findListByRaceId(raceId);
        for (HorseInRace horseInRace : listHorsesInRace) {
            horseInRace.setOddsValues(oddsDao.findListByHorseInRace(horseInRace.getId()));
        }
        return listHorsesInRace;
    }

    public void save(HorseInRace horseInRace) throws ServiceException {
        List<HorseInRace> listStoredHorsesInRace = horseInRaceDao.findListByRaceId(horseInRace.getRaceId());
        for (HorseInRace storedHorseInRace : listStoredHorsesInRace) {
            if (storedHorseInRace.equals(horseInRace)) {
                String message = ExceptionMessages.getMessage(ExceptionMessages.HORSE_ALREADY_IN_RACE);
                LOGGER.warn(message);
                throw new ServiceException(message);
            }
        }
        super.save(horseInRace);
    }

    /**
     * finds list of HorseInRace by certain horse by ID
     * @param horseId
     * @return
     */
    public List<HorseInRace> findListByHorseId(Long horseId) {
        List<HorseInRace> horsesInRace = horseInRaceDao.findListByHorseId(horseId);
        for (HorseInRace horseInRace : horsesInRace) {
            horseInRace.setOddsValues(oddsDao.findListByHorseInRace(horseInRace.getId()));
        }
        return horsesInRace;
    }

    /**
     * returns list of HorseInRace by list of won bets
     * @param wonBets
     * @return
     * @throws ServiceException
     */
    public List<HorseInRace> findBetHorsesInRace(List<Bet> wonBets) throws ServiceException {
        List<HorseInRace> listBetHorses = new ArrayList<>();
        for (Bet wonBet : wonBets) {
            Long horseInRaceId = wonBet.getOdds().getHorseInRaceId();
            HorseInRace horseInRace = findById(horseInRaceId);
            listBetHorses.add(horseInRace);
        }
        return listBetHorses;
    }

    /**
     * deletes HorseInRace even there are some Odds referencing to HorseInRace
     * Founded Odds are deleted too
     * @param horseInRaceId             ID of HorseInRace
     * @throws ServiceException         if database error occurs
     */
    public void delete(Long horseInRaceId) throws ServiceException {
        List<Odds> oddsList = oddsDao.findListByHorseInRace(horseInRaceId);
        for (Odds odds : oddsList) {
            if (!oddsDao.delete(odds.getId())) {
                String message = ExceptionMessages.getMessage(ExceptionMessages.DELETE_FAILED) + " " + oddsDao.getName();
                LOGGER.error(message);
                throw new ServiceException(message);
            }
        }
        if (!dao.delete(horseInRaceId)) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.DELETE_FAILED) + " " + dao.getName();
            LOGGER.error(message);
            throw new ServiceException(message);
        }
    }
}
