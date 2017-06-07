package ua.timonov.web.project.command.race;

import org.apache.log4j.Logger;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.parser.Parser;
import ua.timonov.web.project.parser.ParserFactory;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.LoggerMessages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * deletes race
 */
public class DeleteRaceAction extends GetRacesAction {

    private static final Logger LOGGER = Logger.getLogger(DeleteRaceAction.class);

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();
    private Parser<Long> idParser = ParserFactory.createIdParser();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Long raceId = idParser.parse(request.getParameter(Strings.RACE_ID), Strings.RACE_ID);
        raceService.delete(raceId);
        LOGGER.info(LoggerMessages.RACE_DELETED);
        request.setAttribute(Strings.MESSAGE_SUCCESS, true);
        return prepareRacesPage(request);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        LOGGER.info(LoggerMessages.ERROR_DElETE_RACE);
        request.setAttribute(Strings.MESSAGE_ERROR, e.getMessage());
        request.setAttribute(Strings.ERROR_DETAILS, e.getStackTrace());
        return prepareRacesPage(request);
    }
}
