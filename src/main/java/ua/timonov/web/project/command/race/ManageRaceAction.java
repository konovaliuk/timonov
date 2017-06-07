package ua.timonov.web.project.command.race;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.race.RaceStatus;
import ua.timonov.web.project.parser.Parser;
import ua.timonov.web.project.parser.ParserFactory;
import ua.timonov.web.project.service.*;
import ua.timonov.web.project.util.Pages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * prepares page for managing race
 */
public class ManageRaceAction implements Action {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();
    private HorseService horseService = serviceFactory.createHorseService();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();
    private LocationService locationService = serviceFactory.createLocationService();
    private Parser<Long> idParser = ParserFactory.createIdParser();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Long raceId = idParser.parse(request.getParameter(Strings.RACE_ID), Strings.RACE_ID);
        Race race = raceService.findById(raceId);
        return prepareManageRacePage(request, race);
    }

    protected String prepareManageRacePage(HttpServletRequest request, Race race) throws ServiceException {
        request.setAttribute(Strings.RACE, race);
        request.setAttribute(Strings.HORSES_IN_RACE, horseInRaceService.findListByRaceId(race.getId()));
        request.setAttribute(Strings.RACE_STATUSES, RaceStatus.values());
        request.setAttribute(Strings.RACE_STATUS_BEING_FORMED, RaceStatus.BEING_FORMED);
        request.setAttribute(Strings.RACE_STATUS_FINISHED, RaceStatus.FINISHED);
        request.setAttribute(Strings.RACE_STATUS_WINS_PAID, RaceStatus.WINS_PAID);
        request.setAttribute(Strings.LOCATIONS, locationService.findAll());
        request.setAttribute(Strings.HORSES, horseService.findAll());
        return Pages.getPage(Pages.RACE_MANAGE_PAGE);
    }
}