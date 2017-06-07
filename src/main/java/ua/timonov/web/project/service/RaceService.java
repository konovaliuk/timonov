package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.daointerface.BetDao;
import ua.timonov.web.project.dao.daointerface.HorseDao;
import ua.timonov.web.project.dao.daointerface.HorseInRaceDao;
import ua.timonov.web.project.dao.daointerface.RaceDao;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.bet.BetStatus;
import ua.timonov.web.project.model.bet.BetType;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.race.RaceStatus;
import ua.timonov.web.project.model.user.Money;
import ua.timonov.web.project.util.ExceptionMessages;
import ua.timonov.web.project.util.LoggerMessages;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RaceService extends DataService<Race, HorseInRace> {

    private static final Logger LOGGER = Logger.getLogger(RaceService.class);

    public static final int FINISH_PLACES_SET_CORRECTLY = 0;

    private static RaceDao raceDao = daoFactory.createRaceDao();
    private static HorseInRaceDao horseInRaceDao = daoFactory.createHorseInRaceDao();
    private static HorseDao horseDao = daoFactory.createHorseDao();
    private static BetDao betDao = daoFactory.createBetDao();
    private static BetService betService = ServiceFactory.getInstance().createBetService();

    private static final RaceService instance = new RaceService();

    private RaceService() {
        super(raceDao, horseInRaceDao);
    }

    public static RaceService getInstance() {
        return instance;
    }

    /**
     * finds race by HorseInRace ID
     * @param horseInRaceId
     * @return
     * @throws ServiceException
     */
    public Race findByHorseInRaceId(Long horseInRaceId) throws ServiceException {
        Race race = raceDao.findByForeignId(horseInRaceId, "HorseInRace");
        if (race == null) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.HORSE_IN_RACE_ID + " " + horseInRaceId +
                    " " + ExceptionMessages.HORSE_IN_RACE_NOT_FOUND);
            LOGGER.error(message);
            throw new ServiceException(message);
        }
        return race;
    }

    /**
     * deletes race by ID, only if race has status BEING_FORMED
     * @param id persisted entity
     * @throws ServiceException
     */
    public void delete(Long id) throws ServiceException {
        Race race = findById(id);
        if (race.getRaceStatus() != RaceStatus.BEING_FORMED) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.RACE_CANT_BE_DELETED);
            LOGGER.warn(message);
            throw new ServiceException(message);
        }
        super.delete(id);
    }

    /**
     * cancels race and bets on its horses
     * @param race
     * @throws ServiceException
     */
    public void setCancelStatus(Race race) throws ServiceException {
        if (race.getRaceStatus().ordinal() < RaceStatus.FINISHED.ordinal()) {
            race.setRaceStatus(RaceStatus.CANCELLED);
            raceDao.save(race);
            List<Bet> bets = betDao.findListByRaceId(race.getId());
            for (Bet bet : bets) {
                if (bet.getBetStatus() == BetStatus.MADE) {
                    betService.cancelBet(bet, race);
                }
            }
            LOGGER.info(LoggerMessages.RACE_SET_CANCELLED);
        } else {
            String message = ExceptionMessages.getMessage(ExceptionMessages.RACE_CANT_BE_CANCELLED);
            LOGGER.error(message);
            throw new ServiceException(message);
        }
    }

    /**
     * performs races life cycle
     * @param race
     * @throws ServiceException
     */
    public void setNextStatusIfPossible(Race race) throws ServiceException {
        RaceStatus raceStatus = race.getRaceStatus();
        switch (raceStatus) {
            case BEING_FORMED:
                    checkIfRaceReadyToBeOpen(race);
                    setNextStatus(race);
                break;
            case OPEN_TO_BET:
                    setNextStatus(race);
                break;
            case CLOSED_TO_BET:
                    setNextStatus(race);
                break;
            case FINISHED:
                    checkIfPlacesAreSet(race);
                    setNextStatus(race);
                break;
            case RESULTS_FIXATED:
                    payWins(race);
                    setNextStatus(race);
                break;
            default:
                break;
        }
    }

    /**
     * checks if race has 2 or more horses and each horse has even one bet rate (odds)
     * @param race
     * @throws ServiceException
     */
    private void checkIfRaceReadyToBeOpen(Race race) throws ServiceException {
        if (race.getHorsesInRace().size() <= 1) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.RACE_SHOULD_BE_TWO_HORSES);
            LOGGER.warn(message);
            throw new ServiceException(message);
        }
        HorseInRace horseInRaceWithoutOdds = findHorseInRaceWithoutOdds(race);
        if (horseInRaceWithoutOdds != null) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.HORSE_SHOULD_HAVE_ODDS) +
                    horseInRaceWithoutOdds.getHorse().getName();
            LOGGER.warn(message);
            throw new ServiceException(message);
        }
    }

    /* sets next status for race */
    private void setNextStatus(Race race) throws ServiceException {
        RaceStatus currentStatus = race.getRaceStatus();
        race.setRaceStatus(currentStatus.nextPossibleStatus());
        super.save(race);
    }

    /* checks if not all finish places in race are set */
    private void checkIfPlacesAreSet(Race race) throws ServiceException {
        List<HorseInRace> listOfHorsesInRace = horseInRaceDao.findListByRaceId(race.getId());
        if (!allPlacesAreSet(listOfHorsesInRace)) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.RACE_PLACES_ERROR);
            LOGGER.warn(message);
            throw new ServiceException(message);
        }
    }

    /* checks each finish place is set */
    private boolean allPlacesAreSet(List<HorseInRace> listOfHorsesInRace) {
        for (HorseInRace horseInRace : listOfHorsesInRace) {
            if (horseInRace.getFinishPlace() == 0) {
                return false;
            }
        }
        return true;
    }

    /* finds HorseInRace without set Odds */
    private HorseInRace findHorseInRaceWithoutOdds(Race race) {
        return horseInRaceDao.findHorseInRaceWithoutOdds(race.getId());
    }

    /* executes paying by won bets */
    private void payWins(Race race) throws ServiceException {
        Money racePaidSum = new Money(BigDecimal.ZERO);
        List<Bet> wonBets = findWonBetsByRaceId(race.getId());
        for (Bet wonBet : wonBets) {
            if (wonBet.getBetStatus() == BetStatus.MADE) {
                Money paidBetSum = betService.payWin(wonBet);
                racePaidSum = racePaidSum.add(paidBetSum);
            }
        }
        increaseRacePaidSum(race, racePaidSum);
    }

    /* increases paid sum for race */
    private void increaseRacePaidSum(Race race, Money paidSum) throws ServiceException {
        Money newPaidSum = race.getPaidSum().add(paidSum);
        race.setPaidSum(newPaidSum);
        save(race);
    }

    /* finds list of won bet in race */
    public List<Bet> findWonBetsByRaceId(Long raceId) {
        List<Bet> bets = betDao.findListByRaceId(raceId);
        List<HorseInRace> listHorsesInRace = horseInRaceDao.findListByRaceId(raceId);
        Collections.sort(listHorsesInRace);
        List<Bet> wonBets = new ArrayList<>();
        for (Bet bet : bets) {
            if (isWinningBet(bet, listHorsesInRace)) {
                wonBets.add(bet);
            }
        }
        return wonBets;
    }

    /* defines if bet wins */
    private boolean isWinningBet(Bet bet, List<HorseInRace> listHorsesInRace) {
        BetType betType = bet.getOdds().getBetType();
        Long betHorseInRaceId = bet.getOdds().getHorseInRaceId();
        return horseIsOnPrizePlace(betHorseInRaceId, listHorsesInRace, betType.ordinal() + 1);
    }

    /* defines if horse placed prize place */
    private boolean horseIsOnPrizePlace(Long betHorseInRaceId, List<HorseInRace> listHorsesInRace, int place) {
        for (int i = 0; i < place; i++) {
            if (betHorseInRaceId == listHorsesInRace.get(i).getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * saves inputted finish places
     * @param listOfHorsesInRace
     * @param inputtedPlaces
     * @throws ServiceException
     */
    public void saveInputtedPlaces(List<HorseInRace> listOfHorsesInRace, List<Integer> inputtedPlaces) throws ServiceException {
        int wrongPlace = findWrongInputtedPlace(new ArrayList<>(inputtedPlaces));
        if (wrongPlace != FINISH_PLACES_SET_CORRECTLY) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.ERROR_SETTING_PLACES) + " " + wrongPlace;
            LOGGER.error(message);
            throw new ServiceException(message);
        }
        for (int i = 0; i < listOfHorsesInRace.size(); i++) {
            int finishPlace = inputtedPlaces.get(i);
            HorseInRace horseInRace = listOfHorsesInRace.get(i);
            horseInRace.setFinishPlace(finishPlace);
            horseInRaceDao.save(horseInRace);
            increaseRacesNumberForHorse(horseInRace.getHorse(), finishPlace);
        }
    }

    /* finds wrong inputted finish place */
    private int findWrongInputtedPlace(List<Integer> places) {
        Collections.sort(places);
        for (int i = 0; i < places.size(); i++) {
            if (places.get(i) != i + 1) {
                if (places.get(i) == i) {
                    return i;
                } else {
                    return i + 1;
                }
            }
        }
        return FINISH_PLACES_SET_CORRECTLY;
    }

    /* increases total races and won races if horse won */
    private void increaseRacesNumberForHorse(Horse horse, int finishPlace) {
        horse.setTotalRaces(horse.getTotalRaces() + 1);
        if (finishPlace == 1) {
            horse.setWonRaces(horse.getWonRaces() + 1);
        }
        horseDao.save(horse);
    }

    /**
     * increases bet sum for race by given amount of money
     * @param race
     * @param addedSum
     * @throws ServiceException
     */
    public void increaseBetSum(Race race, Money addedSum) throws ServiceException {
        Money newBetRaceSum = race.getBetSum().add(addedSum);
        race.setBetSum(newBetRaceSum);
        save(race);
    }

    /**
     * decreases bet sum for race by given amount of money
     * @param race
     * @param subtrahendSum
     * @throws ServiceException
     */
    public void decreaseBetSum(Race race, Money subtrahendSum) throws ServiceException {
        Money newBetRaceSum = race.getBetSum().subtract(subtrahendSum);
        race.setBetSum(newBetRaceSum);
        save(race);
    }

    /**
     * returns list of races by bets of certain user
     * @param userBets
     * @return
     * @throws ServiceException
     */
    public List<Race> findBetRaces(List<Bet> userBets) throws ServiceException {
        List<Race> races = new ArrayList<>();
        for (Bet userBet : userBets) {
            long horseInRaceId = userBet.getOdds().getHorseInRaceId();
            Race race = findByHorseInRaceId(horseInRaceId);
            races.add(race);
        }
        return races;
    }
}