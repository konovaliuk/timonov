package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.daointerface.BetDao;
import ua.timonov.web.project.dao.daointerface.HorseInRaceDao;
import ua.timonov.web.project.dao.daointerface.RaceDao;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.bet.BetType;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.race.RaceStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RaceService extends DataService<Race, HorseInRace> {

    private static final Logger LOGGER = Logger.getLogger(RaceService.class);
    private static RaceDao raceDao = daoFactory.createRaceDao();
    private static HorseInRaceDao horseInRaceDao = daoFactory.createHorseInRaceDao();
    private static BetDao betDao = daoFactory.createBetDao();
    private static UserService userService = ServiceFactory.getInstance().createUserService();
    private static final RaceService instance = new RaceService();

    private RaceService() {
        super(raceDao, horseInRaceDao);
    }

    public static RaceService getInstance() {
        return instance;
    }

    public Race findByHorseInRaceId(long horseInRaceId) {
        Race race = raceDao.findByForeignId(horseInRaceId, "HorseInRace");
        if (race == null) {
            LOGGER.error("Horse in race " + horseInRaceId + " does not exist in table Race");
            throw new ServiceException("Horse in race " + horseInRaceId + " does not exist in table Race");
        }
        return race;
    }

    public void delete(long id) throws ServiceException {
        Race race = findById(id);
        if (race.getRaceStatus() != RaceStatus.BEING_FORMED) {
            LOGGER.warn("Race can't be deleted! Should have status \"being formed\"");
            throw new ServiceException("Race can't be deleted! Should have status \"being formed\"");
        }
        super.delete(id);
    }

    public void setCancelStatus(Race race) {
        if (race.getRaceStatus().compareTo(RaceStatus.FINISHED) < 0) {
            race.setRaceStatus(RaceStatus.CANCELLED);
            raceDao.save(race);
            LOGGER.info("Race status is set to \"Cancelled\"");
        } else {
            LOGGER.error("Race status can't be set to \"Cancelled\"");
            throw new ServiceException("Race status can't be set to \"Cancelled\"");
        }
    }

    public void setNextStatusIfPossible(Race race) {
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

    private void checkIfRaceReadyToBeOpen(Race race) {
        if (race.getHorsesInRace().size() <= 1) {
            LOGGER.warn("Should be at least 2 horses in the race to open betting");
            throw new ServiceException("Should be at least 2 horses in the race to open betting");
        }
        HorseInRace horseInRaceWithoutOdds = findHorseInRaceWithoutOdds(race);
        if (horseInRaceWithoutOdds != null) {
            String message = "Each horse in race should have at least one bet rate. Horse " +
                    horseInRaceWithoutOdds.getHorse().getName() + " does not";
            LOGGER.warn(message);
            throw new ServiceException(message);
        }
    }

    private void setNextStatus(Race race) {
        RaceStatus currentStatus = race.getRaceStatus();
        race.setRaceStatus(currentStatus.nextStatus());
        save(race);
    }

    private void checkIfPlacesAreSet(Race race) {
        List<HorseInRace> listOfHorsesInRace = horseInRaceDao.findListByRaceId(race.getId());
        if (!allPlacesAreSet(listOfHorsesInRace)) {
            LOGGER.warn("One or more horse places are not set");
            throw new ServiceException("One or more horse places are not set");
        }
    }

    private boolean allPlacesAreSet(List<HorseInRace> listOfHorsesInRace) {
        for (HorseInRace horseInRace : listOfHorsesInRace) {
            if (horseInRace.getFinishPlace() == 0) {
                return false;
            }
        }
        return true;
    }

    private HorseInRace findHorseInRaceWithoutOdds(Race race) {
        return horseInRaceDao.findHorseInRaceWithoutOdds(race.getId());
    }

    private void payWins(Race race) {
        List<HorseInRace> listHorsesInRace = horseInRaceDao.findListByRaceId(race.getId());
        List<Bet> bets = betDao.findListByRaceId(race.getId());
        for (Bet bet : bets) {
            if (isWinningBet(bet, listHorsesInRace)) {
                userService.payWin(bet);
            }
        }
    }

    private boolean isWinningBet(Bet bet, List<HorseInRace> listHorsesInRace) {
        Collections.sort(listHorsesInRace);
        BetType betType = bet.getOdds().getBetType();
        long betHorseInRaceId = bet.getOdds().getHorseInRaceId();
        switch (betType) {
            case WINNER:
            case SECOND_PLACE:
            case PRIZE_PLACE:
                return horseIsOnPrizePlace(betHorseInRaceId, listHorsesInRace, betType.ordinal() + 1);
            case DOUBLE_EXPRESS:
                return horseIsOnPrizePlace(betHorseInRaceId, listHorsesInRace, 2);
            case TRIPLE_EXPRESS:
                return false;
            case QUINELLA:
                return horseIsOnPrizePlace(betHorseInRaceId, listHorsesInRace, 2);
            default:
                return false;
        }
    }

    private boolean horseIsOnPrizePlace(long betHorseInRaceId, List<HorseInRace> listHorsesInRace, int place) {
        for (int i = 0; i < place; i++) {
            if (betHorseInRaceId == listHorsesInRace.get(i).getId()) {
                return true;
            }
        }
        return false;
    }

    public void saveInputtedPlaces(List<HorseInRace> listOfHorsesInRace, List<Integer> inputtedPlaces) {
        if (validateInputtedPlaces(new ArrayList<>(inputtedPlaces))) {
            for (int i = 0; i < listOfHorsesInRace.size(); i++) {
                HorseInRace horseInRace = listOfHorsesInRace.get(i);
                horseInRace.setFinishPlace(inputtedPlaces.get(i));
                horseInRaceDao.save(horseInRace);
            }
        } else {
            LOGGER.error("Error while fixating results");
            throw new ServiceException("Error while fixating results");
        }
    }

    private boolean validateInputtedPlaces(List<Integer> places) {
        Collections.sort(places);
        for (int i = 0; i < places.size(); i++) {
            if (places.get(i) != i + 1) {
                return false;
            }
        }
        return true;
    }
}