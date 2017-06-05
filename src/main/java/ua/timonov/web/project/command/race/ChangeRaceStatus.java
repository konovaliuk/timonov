package ua.timonov.web.project.command.race;

import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class ChangeRaceStatus extends ManageRaceAction {

    ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();
    private Race race;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Long raceId = Long.valueOf(request.getParameter("raceId"));
        race = raceService.findById(raceId);
        List<HorseInRace> listHorsesInRace = horseInRaceService.findListByRaceId(race.getId());
        race.setHorsesInRace(listHorsesInRace);

        raceService.setNextStatusIfPossible(race);
        request.setAttribute("messageSuccess", true);

        return prepareManageRacePage(request, race);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        request.setAttribute("messageError", e.getMessage());
        request.setAttribute("errorDetails", e.getCause());
        return prepareManageRacePage(request, race);
    }
}
