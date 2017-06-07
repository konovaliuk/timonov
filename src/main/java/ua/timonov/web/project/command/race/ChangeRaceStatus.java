package ua.timonov.web.project.command.race;

import org.apache.log4j.Logger;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.parser.Parser;
import ua.timonov.web.project.parser.ParserFactory;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.LoggerMessages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * changes race status
 */
public class ChangeRaceStatus extends ManageRaceAction {

    private static final Logger LOGGER = Logger.getLogger(CancelRaceAction.class);

    ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();
    private Parser<Long> idParser = ParserFactory.createIdParser();
    private Race race;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Long raceId = idParser.parse(request.getParameter(Strings.RACE_ID), Strings.RACE_ID);
        race = raceService.findById(raceId);
        List<HorseInRace> listHorsesInRace = horseInRaceService.findListByRaceId(race.getId());
        race.setHorsesInRace(listHorsesInRace);
        raceService.setNextStatusIfPossible(race);
        LOGGER.info(LoggerMessages.RACE_STATUS_CHANGED);
        request.setAttribute(Strings.MESSAGE_SUCCESS, true);
        return prepareManageRacePage(request, race);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        LOGGER.info(LoggerMessages.ERROR_CHANGE_RACE_STATUS);
        request.setAttribute(Strings.MESSAGE_ERROR, e.getMessage());
        request.setAttribute(Strings.ERROR_DETAILS, e.getStackTrace());
        return prepareManageRacePage(request, race);
    }
}
