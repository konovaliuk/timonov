package ua.timonov.web.project.command.race;

import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.model.location.Location;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.race.RaceStatus;
import ua.timonov.web.project.parser.FactoryParser;
import ua.timonov.web.project.parser.Parser;
import ua.timonov.web.project.service.LocationService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class SaveEditedRaceAttributesAction extends ManageRaceAction {

    public static final String DATE = "date";
    public static final String SPACE = " ";
    public static final String UNDERSCORE = "_";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();
    private LocationService locationService = serviceFactory.createLocationService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Race race = createRaceFromRequest(request);
        raceService.save(race);
        request.setAttribute("messageSuccess", true);
        return prepareManageRacePage(request, race);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        Long raceId = FactoryParser.createIdParser().parse(request.getParameter("raceId"), "raceId");
        Race race = raceService.findById(raceId);
        request.setAttribute("messageError", e.getMessage());
        request.setAttribute("errorDetails", e.getCause());
        return prepareManageRacePage(request, race);
    }

    private Race createRaceFromRequest(HttpServletRequest request) throws AppException {
        String parameterId = request.getParameter("raceId");
        Long id = parameterId != null ? Long.valueOf(parameterId) : 0;
        Long locationId = Long.valueOf(request.getParameter("location"));
        String dateValue = request.getParameter(DATE);
        Parser<Date> dateParser = FactoryParser.createDateParserInputTypeText();
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
