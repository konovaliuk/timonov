package ua.timonov.web.project.command;

import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.race.RaceStatus;
import ua.timonov.web.project.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddHorseInRaceAction extends Action {

    public static final String RACE_EDIT = "raceEdit";

    ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();
    private HorseService horseService = serviceFactory.createHorseService();
    private RaceService raceService = serviceFactory.createRaceService();
    private LocationService locationService = serviceFactory.createLocationService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        HorseInRace horseInRace = createHorseFromRequest(request);
        try {
            horseInRaceService.save(horseInRace);
            request.setAttribute("messageSuccess", true);
        } catch (ServiceException e) {
            request.setAttribute("messageError", e.getMessage());
            request.setAttribute("errorDetails", e.getCause());
        }
        Race race = raceService.findById(horseInRace.getRaceId());
        request.setAttribute("race", race);
        request.setAttribute("horsesInRace", horseInRaceService.findListByRaceId(race.getId()));
        request.setAttribute("raceStatuses", RaceStatus.values());
        request.setAttribute("locations", locationService.findAll());
        request.setAttribute("horses", horseService.findAll());
        return CONFIG.getString(RACE_EDIT);
    }

    private HorseInRace createHorseFromRequest(HttpServletRequest request) {
        Long raceId = Long.valueOf(request.getParameter("raceId"));
        Long horseId = Long.valueOf(request.getParameter("horseId"));
        Horse horse = horseService.findById(horseId);
        return new HorseInRace(raceId, horse);
    }
}
