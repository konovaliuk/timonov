package ua.timonov.web.project.command.race;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.service.LocationService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetRacesAction extends Action {

    public static final String RACES = "races";
    public static final String LOCATIONS = "locations";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();
    private LocationService locationService = serviceFactory.createLocationService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return prepareRacesPage(request);
    }

    protected String prepareRacesPage(HttpServletRequest request) {
        request.setAttribute(RACES, raceService.findAll());
        request.setAttribute(LOCATIONS, locationService.findAll());
        return Pages.getPage(Pages.RACES_PAGE);
    }
}
