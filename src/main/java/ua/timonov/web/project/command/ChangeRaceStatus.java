package ua.timonov.web.project.command;

import ua.timonov.web.project.command.race.EditRaceAction;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class ChangeRaceStatus extends EditRaceAction {

    public static final String RACE_EDIT = "raceEdit";

    ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();
    private LocationService locationService = serviceFactory.createLocationService();
    private HorseService horseService = serviceFactory.createHorseService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        long raceId = Long.valueOf(request.getParameter("raceId"));
        Race race = raceService.findById(raceId);
        List<HorseInRace> listHorsesInRace = horseInRaceService.findListByRaceId(race.getId());
        race.setHorsesInRace(listHorsesInRace);
        try {
            raceService.setNextStatusIfPossible(race);
            request.setAttribute("messageSuccess", true);
        } catch (ServiceException e) {
            request.setAttribute("messageError", e.getMessage());
            request.setAttribute("errorDetails", e.getCause());
        }
        return prepareEditRacePage(request, race);
    }
}
