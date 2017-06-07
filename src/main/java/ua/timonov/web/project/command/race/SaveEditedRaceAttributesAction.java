package ua.timonov.web.project.command.race;

import org.apache.log4j.Logger;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.model.location.Location;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.race.RaceStatus;
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
 * saves race after editing its attributes
 */
public class SaveEditedRaceAttributesAction extends ManageRaceAction {

    private static final Logger LOGGER = Logger.getLogger(CancelRaceAction.class);

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();
    private LocationService locationService = serviceFactory.createLocationService();
    private Parser<Long> idParser = ParserFactory.createIdParser();
    private Parser<Date> dateParser = ParserFactory.createDateParserInputTypeText();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Race race = createRaceFromRequest(request);
        raceService.save(race);
        LOGGER.info(LoggerMessages.RACE_SAVE_EDITED);
        request.setAttribute(Strings.MESSAGE_SUCCESS, true);
        return prepareManageRacePage(request, race);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        Long raceId = idParser.parse(request.getParameter(Strings.RACE_ID), Strings.RACE_ID);
        Race race = raceService.findById(raceId);
        LOGGER.info(LoggerMessages.ERROR_EDITING_RACE);
        request.setAttribute(Strings.MESSAGE_ERROR, e.getMessage());
        request.setAttribute(Strings.ERROR_DETAILS, e.getStackTrace());
        return prepareManageRacePage(request, race);
    }

    private Race createRaceFromRequest(HttpServletRequest request) throws AppException {
        String parameterId = request.getParameter(Strings.RACE_ID);
        Long id = parameterId != null ? idParser.parse(parameterId, Strings.RACE_ID) : 0L;
        Long locationId = Long.valueOf(request.getParameter(Strings.LOCATION));
        Date date = dateParser.parse(request.getParameter(Strings.DATE), Strings.DATE);
        RaceStatus raceStatus = RaceStatus.valueOf(stringToEnumView(request.getParameter(Strings.RACE_STATUS)));
        Location location = locationService.findById(locationId);
        return new Race.Builder(location, date)
                .id(id)
                .raceStatus(raceStatus)
                .build();
    }

    private String stringToEnumView(String raceStatus) {
        return raceStatus.toUpperCase().replace(Strings.SPACE, Strings.UNDERSCORE);
    }
}
