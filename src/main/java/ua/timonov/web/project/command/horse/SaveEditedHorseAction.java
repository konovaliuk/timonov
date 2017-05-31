package ua.timonov.web.project.command.horse;


import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.service.HorseService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveEditedHorseAction extends ua.timonov.web.project.command.Action {

    public static final String HORSE_EDIT_PAGE = "horseEdit";
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseService horseService = serviceFactory.createHorseService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        Horse horse = createHorseFromRequest(request);
        try {
            horseService.validateNumberOfWonRaces(horse);
            horseService.save(horse);
            request.setAttribute("messageSuccess", true);
        } catch (ServiceException e) {
            request.setAttribute("messageError", e.getMessage());
            request.setAttribute("errorDetails", e.getCause());
        }
        request.setAttribute("horse", horse);
        return CONFIG.getString(HORSE_EDIT_PAGE);
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
