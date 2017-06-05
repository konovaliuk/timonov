package ua.timonov.web.project.command.horseinrace;

import ua.timonov.web.project.command.race.ManageRaceAction;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteHorseInRaceAction extends ManageRaceAction {

    ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();
    private RaceService raceService = serviceFactory.createRaceService();
    private Race race;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Long horseInRaceId = Long.valueOf(request.getParameter("horseInRace"));
        race = raceService.findByHorseInRaceId(horseInRaceId);
        horseInRaceService.delete(horseInRaceId);
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
