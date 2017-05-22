package ua.timonov.web.project.command;

import ua.timonov.web.project.model.location.Location;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.race.RaceStatus;
import ua.timonov.web.project.parser.FactoryParser;
import ua.timonov.web.project.parser.Parser;
import ua.timonov.web.project.parser.ParsingException;
import ua.timonov.web.project.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class SaveEditedRaceAction extends Action {

    public static final String RACE_EDIT_PAGE = "/WEB-INF/jsp/raceEdit.jsp";
    public static final String DATE = "date";

    private RaceService raceService = ServiceFactory.getInstance().getRaceService();
    private HorseInRaceService horseInRaceService = ServiceFactory.getInstance().getHorseInRaceService();
    private CountryService countryService = ServiceFactory.getInstance().getCountryService();
    private LocationService locationService = ServiceFactory.getInstance().getLocationService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ParsingException, DataServiceException {
        Race race = createRaceFromRequest(request);
        RaceStatus raceStatus = RaceStatus.valueOf(request.getParameter("raceStatus"));
        race.setRaceStatus(raceStatus);
        raceService.save(race);

//        long raceId = Long.valueOf(request.getParameter("raceId"));
//        Race race = raceService.findById(raceId);

        request.setAttribute("race", race);
        request.setAttribute("horsesInRace", horseInRaceService.getByRace(race.getId()));
        request.setAttribute("raceStatuses", RaceStatus.values());
        request.setAttribute("countries", countryService.getAll());
        request.setAttribute("locations", locationService.getAll());
        return RACE_EDIT_PAGE;
    }

    private Race createRaceFromRequest(HttpServletRequest request) throws ParsingException, DataServiceException {
        String parameterId = request.getParameter("id");
        long id = parameterId != null ? Long.valueOf(parameterId) : 0;
        long locationId = Long.valueOf(request.getParameter("location"));
        Parser<Date> dateParser = FactoryParser.createDateParser();
        String dateValue = request.getParameter(DATE);
        Date date = dateParser.parse(dateValue, DATE);
        RaceStatus raceStatus = RaceStatus.valueOf(request.getParameter("raceStatus"));
        Location location = locationService.findById(locationId);
        return new Race(id, raceStatus, location, date);
    }
}
