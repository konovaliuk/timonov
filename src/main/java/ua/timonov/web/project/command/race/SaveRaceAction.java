package ua.timonov.web.project.command.race;

import org.apache.log4j.Logger;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.model.location.Location;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.parser.ParserFactory;
import ua.timonov.web.project.parser.Parser;
import ua.timonov.web.project.service.LocationService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.LoggerMessages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * saves new race
 */
public class SaveRaceAction extends GetRacesAction {

    private static final Logger LOGGER = Logger.getLogger(CancelRaceAction.class);

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();
    private LocationService locationService = serviceFactory.createLocationService();
    private Parser<Long> idParser = ParserFactory.createIdParser();
    private Parser<Date> dateParser = ParserFactory.createDateParserInputTypeDate();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Race race = createRaceFromRequest(request);
        raceService.save(race);
        LOGGER.info(LoggerMessages.RACE_ADDED);
        request.setAttribute(Strings.MESSAGE_SUCCESS, true);
        return prepareRacesPage(request);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        LOGGER.info(LoggerMessages.ERROR_ADD_RACE);
        request.setAttribute(Strings.MESSAGE_ERROR, e.getMessage());
        request.setAttribute(Strings.ERROR_DETAILS, e.getStackTrace());
        return prepareRacesPage(request);
    }

    private Race createRaceFromRequest(HttpServletRequest request) throws AppException {
        Long locationId = idParser.parse(request.getParameter(Strings.LOCATION_ID), Strings.LOCATION_ID);
        Location location = locationService.findById(locationId);
        String dateValue = request.getParameter(Strings.DATE);
        Date date = dateParser.parse(dateValue, Strings.DATE);
        return new Race.Builder(location, date).build();
    }
}
