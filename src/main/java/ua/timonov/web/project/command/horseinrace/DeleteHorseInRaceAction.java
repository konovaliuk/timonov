package ua.timonov.web.project.command.horseinrace;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.race.RaceStatus;
import ua.timonov.web.project.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteHorseInRaceAction extends Action {

    public static final String RACE_EDIT_PAGE = "/WEB-INF/jsp/raceEdit.jsp";

    ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();
    private RaceService raceService = serviceFactory.createRaceService();
    private CountryService countryService = serviceFactory.createCountryService();
    private LocationService locationService = serviceFactory.createLocationService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        long horseInRaceId = Long.valueOf(request.getParameter("horseInRace"));
        horseInRaceService.delete(horseInRaceId);

        Race race = raceService.findByHorseInRaceId(horseInRaceId);
        request.setAttribute("race", race);
        request.setAttribute("horsesInRace", horseInRaceService.findByRaceId(race.getId()));
        request.setAttribute("raceStatuses", RaceStatus.values());
        request.setAttribute("countries", countryService.findAll());
        request.setAttribute("locations", locationService.findAll());

        return RACE_EDIT_PAGE;
    }
}
