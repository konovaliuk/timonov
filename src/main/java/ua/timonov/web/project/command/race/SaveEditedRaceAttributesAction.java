package ua.timonov.web.project.command.race;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.location.Location;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.race.RaceStatus;
import ua.timonov.web.project.parser.FactoryParser;
import ua.timonov.web.project.parser.Parser;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class SaveEditedRaceAttributesAction extends Action {

    public static final String RACE_EDIT = "raceEdit";
    public static final String DATE = "date";
    public static final String SPACE = " ";
    public static final String UNDERSCORE = "_";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();
    private HorseService horseService = serviceFactory.createHorseService();
    private LocationService locationService = serviceFactory.createLocationService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ParsingException, ServiceException {
        Race race = createRaceFromRequest(request);
        try {
            raceService.save(race);
            request.setAttribute("messageSuccess", true);
        } catch (ServiceException e) {
            request.setAttribute("messageError", e.getMessage());
            request.setAttribute("errorDetails", e.getCause());
        }
        request.setAttribute("race", race);
        request.setAttribute("horsesInRace", horseInRaceService.findListByRaceId(race.getId()));
        request.setAttribute("raceStatuses", RaceStatus.values());
        request.setAttribute("locations", locationService.findAll());
        request.setAttribute("horses", horseService.findAll());
        return CONFIG.getString(RACE_EDIT);
    }

    private Race createRaceFromRequest(HttpServletRequest request) throws ParsingException, ServiceException {
        String parameterId = request.getParameter("raceId");
        long id = parameterId != null ? Long.valueOf(parameterId) : 0;
        long locationId = Long.valueOf(request.getParameter("location"));
        // TODO with Date
        Parser<Date> dateParser = FactoryParser.createDateParser();
        String dateValue = request.getParameter(DATE);
        Date date = dateParser.parse(dateValue, DATE);
        RaceStatus raceStatus = RaceStatus.valueOf(stringToEnumView(request.getParameter("raceStatus")));
        Location location = locationService.findById(locationId);
        return new Race.Builder(location, date)
                .id(id)
                .raceStatus(raceStatus)
                .build();
    }

    private String stringToEnumView(String raceStatus) {
        return raceStatus.toUpperCase().replace(SPACE, UNDERSCORE);
    }
}
