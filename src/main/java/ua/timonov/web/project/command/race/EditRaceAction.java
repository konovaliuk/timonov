package ua.timonov.web.project.command.race;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.race.RaceStatus;
import ua.timonov.web.project.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditRaceAction extends Action {

    public static final String RACE_EDIT = "raceEdit";

    private RaceService raceService = ServiceFactory.getInstance().createRaceService();
    private HorseInRaceService horseInRaceService = ServiceFactory.getInstance().createHorseInRaceService();
    private CountryService countryService = ServiceFactory.getInstance().createCountryService();
    private LocationService locationService = ServiceFactory.getInstance().createLocationService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        long raceId = Long.valueOf(request.getParameter("raceId"));
        Race race = raceService.findById(raceId);
        request.setAttribute("race", race);
        request.setAttribute("horsesInRace", horseInRaceService.findByRaceId(raceId));
        request.setAttribute("raceStatuses", RaceStatus.values());
        request.setAttribute("countries", countryService.findAll());
        request.setAttribute("locations", locationService.findAll());
//        return choosePage(raceStatus);
        return CONFIG.getString(RACE_EDIT);
    }

    @Deprecated
    private String choosePage(RaceStatus raceStatus) {
        return "/WEB-INF/jsp/race/edit" + raceStatus.nameForJspFile() + ".jsp";
    }
}
