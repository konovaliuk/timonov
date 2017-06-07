package ua.timonov.web.project.command.horseinrace;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.bet.BetType;
import ua.timonov.web.project.model.race.RaceStatus;
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
 * finds horse in race for bookmaker page
 */
public class GetHorseInRaceBookieAction implements Action {

    private HorseInRaceService horseInRaceService = ServiceFactory.getInstance().createHorseInRaceService();
    private RaceService raceService = ServiceFactory.getInstance().createRaceService();
    private Parser<Long> idParser = ParserFactory.createIdParser();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Long horseInRaceId = idParser.parse(request.getParameter(Strings.HORSE_IN_RACE_ID), Strings.HORSE_IN_RACE_ID);
        return prepareHorseInRacePage(request, horseInRaceId);
    }

    protected String prepareHorseInRacePage(HttpServletRequest request, long horseInRaceId) throws ServiceException {
        request.setAttribute(Strings.HORSE_IN_RACE, horseInRaceService.findById(horseInRaceId));
        request.setAttribute(Strings.RACE, raceService.findByHorseInRaceId(horseInRaceId));
        request.setAttribute(Strings.RACE_STATUS_OPEN, RaceStatus.OPEN_TO_BET);
        request.setAttribute(Strings.BET_TYPES, BetType.values());
        return Pages.getPage(Pages.HORSE_IN_RACE_BOOKIE_PAGE);
    }
}
