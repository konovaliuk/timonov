package ua.timonov.web.project.command.race;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.race.RaceStatus;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetRaceAction implements Action {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        Long raceId = Long.valueOf(request.getParameter("raceId"));
        request.setAttribute("race", raceService.findById(raceId));
        request.setAttribute("openRaceStatus", RaceStatus.OPEN_TO_BET);
        request.setAttribute("horsesInRace", horseInRaceService.findListByRaceId(raceId));
        return Pages.getPage(Pages.RACE_PAGE);
    }
}
