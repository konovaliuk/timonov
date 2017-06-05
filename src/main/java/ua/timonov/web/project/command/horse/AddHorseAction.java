package ua.timonov.web.project.command.horse;

import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.service.HorseService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddHorseAction extends GetHorsesAction {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseService horseService = serviceFactory.createHorseService();
    private Horse horse;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        horse = createHorseFromRequest(request);
        horseService.validateNumberOfWonRaces(horse);
        horseService.save(horse);
        request.setAttribute("messageSuccess", true);
        return prepareRacesPage(request);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        request.setAttribute("messageError", e.getMessage());
        request.setAttribute("errorDetails", e.getCause());
        request.setAttribute("horseWithInputError", horse);
        return prepareRacesPage(request);
    }

    private Horse createHorseFromRequest(HttpServletRequest request) {
        String name = request.getParameter("name");
        int year = Integer.valueOf(request.getParameter("year"));
        int totalRaces = Integer.valueOf(request.getParameter("totalRaces"));
        int wonRaces = Integer.valueOf(request.getParameter("wonRaces"));
        return new Horse(name, year, totalRaces, wonRaces);
    }
}
