package ua.timonov.web.project.command;

import ua.timonov.web.project.exception.ServiceLayerException;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.race.RaceStatus;
import ua.timonov.web.project.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditRaceAction extends Action {

    public static final String RACE_EDIT_PAGE = "/WEB-INF/jsp/raceEdit.jsp";

    private RaceService raceService = ServiceFactory.getInstance().createRaceService();
    private HorseInRaceService horseInRaceService = ServiceFactory.getInstance().createHorseInRaceService();
    private CountryService countryService = ServiceFactory.getInstance().createCountryService();
    private LocationService locationService = ServiceFactory.getInstance().createLocationService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceLayerException {
        long raceId = Long.valueOf(request.getParameter("raceId"));
        Race race = raceService.getById(raceId);
        request.setAttribute("race", race);
        request.setAttribute("horsesInRace", horseInRaceService.getByRace(raceId));
        request.setAttribute("raceStatuses", RaceStatus.values());
        request.setAttribute("countries", countryService.getAll());
        request.setAttribute("locations", locationService.getAll());
//        return choosePage(raceStatus);
        return RACE_EDIT_PAGE;
    }

    private String choosePage(RaceStatus raceStatus) {
        return "/WEB-INF/jsp/race/edit" + raceStatus.nameForJspFile() + ".jsp";
    }
}
