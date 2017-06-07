package ua.timonov.web.project.command.race;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.race.RaceStatus;
import ua.timonov.web.project.parser.Parser;
import ua.timonov.web.project.parser.ParserFactory;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.Pages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * finds race by ID
 */
public class GetRaceAction implements Action {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();
    private Parser<Long> idParser = ParserFactory.createIdParser();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ParsingException {
        Long raceId = idParser.parse(request.getParameter(Strings.RACE_ID), Strings.RACE_ID);
        request.setAttribute(Strings.RACE, raceService.findById(raceId));
        request.setAttribute(Strings.OPEN_RACE_STATUS, RaceStatus.OPEN_TO_BET);
        request.setAttribute(Strings.HORSES_IN_RACE, horseInRaceService.findListByRaceId(raceId));
        return Pages.getPage(Pages.RACE_PAGE);
    }
}
