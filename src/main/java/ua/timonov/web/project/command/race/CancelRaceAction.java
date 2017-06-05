package ua.timonov.web.project.command.race;

import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CancelRaceAction extends ManageRaceAction {

    ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();
    private Race race;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Long raceId = Long.valueOf(request.getParameter("raceId"));
        race = raceService.findById(raceId);

        raceService.setCancelStatus(race);
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
