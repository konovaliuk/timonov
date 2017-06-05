package ua.timonov.web.project.command.horseinrace;

import ua.timonov.web.project.command.race.ManageRaceAction;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteHorseInRaceAction extends ManageRaceAction {

    ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();
    private RaceService raceService = serviceFactory.createRaceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        long horseInRaceId = Long.valueOf(request.getParameter("horseInRace"));
        Race race = raceService.findByHorseInRaceId(horseInRaceId);
        try {
            horseInRaceService.delete(horseInRaceId);
            request.setAttribute("messageSuccess", true);
        } catch (ServiceException e) {
            request.setAttribute("messageError", e.getMessage());
            request.setAttribute("errorDetails", e.getCause());
        }
        return prepareEditRacePage(request, race);
    }
}
