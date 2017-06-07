package ua.timonov.web.project.command.horseinrace;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.parser.ParserFactory;
import ua.timonov.web.project.parser.Parser;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.Pages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * finds horse in race by ID
 */
public class GetHorseInRaceAction implements Action {

    private HorseInRaceService horseInRaceService = ServiceFactory.getInstance().createHorseInRaceService();
    private RaceService raceService = ServiceFactory.getInstance().createRaceService();
    private Parser<Long> idParser = ParserFactory.createIdParser();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Long id = idParser.parse(request.getParameter(Strings.HORSE_IN_RACE_ID), Strings.HORSE_IN_RACE_ID);
        request.setAttribute(Strings.HORSE_IN_RACE, horseInRaceService.findById(id));
        request.setAttribute(Strings.RACE, raceService.findByHorseInRaceId(id));
        return Pages.getPage(Pages.HORSE_IN_RACE_PAGE);
    }
}
