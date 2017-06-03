package ua.timonov.web.project.command.race;

import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.location.Location;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.parser.FactoryParser;
import ua.timonov.web.project.parser.Parser;
import ua.timonov.web.project.service.LocationService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class SaveRaceAction extends GetRacesAction {

    public static final String RACES = "races";
    public static final String DATE = "date";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();
    private LocationService locationService = serviceFactory.createLocationService();
    private Parser<Date> dateParser = FactoryParser.createDateParser();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        Race race = createRaceFromRequest(request);
        try {
            raceService.save(race);
            request.setAttribute("messageSuccess", true);
        } catch (ServiceException e) {
            request.setAttribute("messageError", e.getMessage());
            request.setAttribute("errorDetails", e.getCause());
        }
        return prepareRacesPage(request);
    }

    private Race createRaceFromRequest(HttpServletRequest request) {
        long locationId = Long.valueOf(request.getParameter("locationId"));
        Location location = locationService.findById(locationId);
        String dateValue = request.getParameter(DATE);
        Date date = dateParser.parse(dateValue, DATE);
        return new Race(location, date);
    }
}
