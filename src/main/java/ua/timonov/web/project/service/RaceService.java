package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.daointerface.BetDao;
import ua.timonov.web.project.dao.daointerface.HorseInRaceDao;
import ua.timonov.web.project.dao.daointerface.RaceDao;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.bet.BetStatus;
import ua.timonov.web.project.model.bet.BetType;
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
    private static BetDao betDao = daoFactory.createBetDao();
    private static BetService betService = ServiceFactory.getInstance().createBetService();
    private static final RaceService instance = new RaceService();

    private RaceService() {
        super(raceDao, horseInRaceDao);
    }

    public static RaceService getInstance() {
        return instance;
    }

    public Race findByHorseInRaceId(long horseInRaceId) throws ServiceException {
        Race race = raceDao.findByForeignId(horseInRaceId, "HorseInRace");
        if (race == null) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.HORSE_IN_RACE_ID + " " + horseInRaceId +
                    " " + ExceptionMessages.HORSE_IN_RACE_NOT_FOUND);
            LOGGER.error(message);
            throw new ServiceException(message);
        }
        return race;
    }

    public void delete(long id) throws ServiceException {
        Race race = findById(id);
        if (race.getRaceStatus() != RaceStatus.BEING_FORMED) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.RACE_CANT_BE_DELETED);
            LOGGER.warn(message);
            throw new ServiceException(message);
        }
        super.delete(id);
    }

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

    private void setNextStatus(Race race) throws ServiceException {
        RaceStatus currentStatus = race.getRaceStatus();
        race.setRaceStatus(currentStatus.nextPossibleStatus());
        super.save(race);
    }

    private void checkIfPlacesAreSet(Race race) throws ServiceException {
        List<HorseInRace> listOfHorsesInRace = horseInRaceDao.findListByRaceId(race.getId());
        if (!allPlacesAreSet(listOfHorsesInRace)) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.RACE_PLACES_ERROR);
            LOGGER.warn(message);
            throw new ServiceException(message);
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

    private void payWins(Race race) throws ServiceException {
        Money racePaidSum = new Money(BigDecimal.ZERO);
        List<Bet> wonBets = findWonBetsByRaceId(race.getId());
        for (Bet wonBet : wonBets) {
            Money paidBetSum = betService.payWin(wonBet);
            racePaidSum.add(paidBetSum);
        }
        increaseRacePaidSum(race, racePaidSum);
    }

    private void increaseRacePaidSum(Race race, Money paidSum) throws ServiceException {
        Money newPaidSum = race.getPaidSum().add(paidSum);
        race.setPaidSum(newPaidSum);
        save(race);
    }

    public List<Bet> findWonBetsByRaceId(long raceId) {
        List<Bet> bets = betDao.findListByRaceId(raceId);
        List<HorseInRace> listHorsesInRace = horseInRaceDao.findListByRaceId(raceId);
        List<Bet> wonBets = new ArrayList<>();
        for (Bet bet : bets) {
            if (isWinningBet(bet, listHorsesInRace)) {
                wonBets.add(bet);
            }
        }
        return wonBets;
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

    public void saveInputtedPlaces(List<HorseInRace> listOfHorsesInRace, List<Integer> inputtedPlaces) throws ServiceException {
        int wrongPlace = findWrongInputtedPlace(new ArrayList<>(inputtedPlaces));
        if (wrongPlace == FINISH_PLACES_SET_CORRECTLY) {
            for (int i = 0; i < listOfHorsesInRace.size(); i++) {
                HorseInRace horseInRace = listOfHorsesInRace.get(i);
                horseInRace.setFinishPlace(inputtedPlaces.get(i));
                horseInRaceDao.save(horseInRace);
            }
        } else {
            String message = ExceptionMessages.getMessage(ExceptionMessages.ERROR_SETTING_PLACES) + " " + wrongPlace;
            LOGGER.error(message);
            throw new ServiceException(message);
        }
    }

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

    public void increaseBetSum(Race race, Money addedSum) throws ServiceException {
        Money newBetRaceSum = race.getBetSum().add(addedSum);
        race.setBetSum(newBetRaceSum);
        save(race);
    }

    public void decreaseBetSum(Race race, Money subtrahendSum) throws ServiceException {
        Money newBetRaceSum = race.getBetSum().subtract(subtrahendSum);
        race.setBetSum(newBetRaceSum);
        save(race);
    }

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