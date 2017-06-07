package ua.timonov.web.project.command.race;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.service.LocationService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.Pages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * finds all races
 */
public class GetRacesAction implements Action {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();
    private LocationService locationService = serviceFactory.createLocationService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        return prepareRacesPage(request);
    }

    protected String prepareRacesPage(HttpServletRequest request) throws ServiceException {
        request.setAttribute(Strings.RACES, raceService.findAll());
        request.setAttribute(Strings.LOCATIONS, locationService.findAll());
        return Pages.getPage(Pages.RACES_PAGE);
    }
}
