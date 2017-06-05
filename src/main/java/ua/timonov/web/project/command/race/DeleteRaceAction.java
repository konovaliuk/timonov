package ua.timonov.web.project.command.race;

import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteRaceAction extends GetRacesAction {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Long raceId = Long.valueOf(request.getParameter("raceId"));

        raceService.delete(raceId);
        request.setAttribute("messageSuccess", true);

        return prepareRacesPage(request);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        request.setAttribute("messageError", e.getMessage());
        request.setAttribute("errorDetails", e.getCause());
        return prepareRacesPage(request);
    }
}
