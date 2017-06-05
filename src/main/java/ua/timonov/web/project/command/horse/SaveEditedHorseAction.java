package ua.timonov.web.project.command.horse;


import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.service.HorseService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveEditedHorseAction implements Action {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseService horseService = serviceFactory.createHorseService();
    private Horse horse;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        horse = createHorseFromRequest(request);

        horseService.validateNumberOfWonRaces(horse);
        horseService.save(horse);
        request.setAttribute("messageSuccess", true);

        request.setAttribute("horse", horse);
        return Pages.getPage(Pages.HORSE_EDIT_PAGE);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        request.setAttribute("messageError", e.getMessage());
        request.setAttribute("errorDetails", e.getCause());
        request.setAttribute("horse", horse);
        return Pages.getPage(Pages.HORSE_EDIT_PAGE);
    }

    private Horse createHorseFromRequest(HttpServletRequest request) {
        long id = Long.valueOf(request.getParameter("horseId"));
        String name = request.getParameter("name");
        int year = Integer.valueOf(request.getParameter("year"));
        int totalRaces = Integer.valueOf(request.getParameter("totalRaces"));
        int wonRaces = Integer.valueOf(request.getParameter("wonRaces"));
        return new Horse(id, name, year, totalRaces, wonRaces);
    }
}
