package ua.timonov.web.project.command.horseinrace;

import org.apache.log4j.Logger;
import ua.timonov.web.project.command.race.ManageRaceAction;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.HorseService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.LoggerMessages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * adds horse in race
 */
public class AddHorseInRaceAction extends ManageRaceAction {

    private static final Logger LOGGER = Logger.getLogger(AddHorseInRaceAction.class);

    ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();
    private HorseService horseService = serviceFactory.createHorseService();
    private RaceService raceService = serviceFactory.createRaceService();
    private Race race;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        HorseInRace horseInRace = createHorseFromRequest(request);
        race = raceService.findById(horseInRace.getRaceId());
        horseInRaceService.save(horseInRace);
        LOGGER.info(LoggerMessages.HORSE_IN_RACE_ADDED + horseInRace.getHorse().getName());
        request.setAttribute(Strings.MESSAGE_SUCCESS, true);
        return prepareManageRacePage(request, race);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        LOGGER.warn(LoggerMessages.ERROR_ADD_HORSE_IN_RACE);
        request.setAttribute(Strings.MESSAGE_ERROR, e.getMessage());
        request.setAttribute(Strings.ERROR_DETAILS, e.getStackTrace());
        return prepareManageRacePage(request, race);
    }

    private HorseInRace createHorseFromRequest(HttpServletRequest request) throws ServiceException {
        Long raceId = Long.valueOf(request.getParameter(Strings.RACE_ID));
        Long horseId = Long.valueOf(request.getParameter(Strings.HORSE_ID));
        Horse horse = horseService.findById(horseId);
        return new HorseInRace(raceId, horse);
    }
}
