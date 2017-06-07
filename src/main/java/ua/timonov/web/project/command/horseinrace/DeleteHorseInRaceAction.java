package ua.timonov.web.project.command.horseinrace;

import org.apache.log4j.Logger;
import ua.timonov.web.project.command.race.ManageRaceAction;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.parser.ParserFactory;
import ua.timonov.web.project.parser.Parser;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.LoggerMessages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * removes horse from race
 */
public class DeleteHorseInRaceAction extends ManageRaceAction {

    private static final Logger LOGGER = Logger.getLogger(DeleteHorseInRaceAction.class);

    ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();
    private RaceService raceService = serviceFactory.createRaceService();
    private Parser<Long> idParser = ParserFactory.createIdParser();
    private Race race;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Long horseInRaceId = idParser.parse(request.getParameter(Strings.HORSE_IN_RACE), Strings.HORSE_IN_RACE);
        race = raceService.findByHorseInRaceId(horseInRaceId);
        horseInRaceService.delete(horseInRaceId);
        LOGGER.info(LoggerMessages.HORSE_IN_RACE_DELETED);
        request.setAttribute(Strings.MESSAGE_SUCCESS, true);
        return prepareManageRacePage(request, race);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        LOGGER.warn(LoggerMessages.ERROR_DElETE_HORSE_IN_RACE);
        request.setAttribute(Strings.MESSAGE_ERROR, e.getMessage());
        request.setAttribute(Strings.ERROR_DETAILS, e.getCause());
        return prepareManageRacePage(request, race);
    }
}
