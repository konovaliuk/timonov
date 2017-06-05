package ua.timonov.web.project.command.horseinrace;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetHorseInRaceAction implements Action {

    private HorseInRaceService horseInRaceService = ServiceFactory.getInstance().createHorseInRaceService();
    private RaceService raceService = ServiceFactory.getInstance().createRaceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        long id = Long.valueOf(request.getParameter("horseInRaceId"));
        request.setAttribute("horseInRace", horseInRaceService.findById(id));
        request.setAttribute("race", raceService.findByHorseInRaceId(id));
        return Pages.getPage(Pages.HORSE_IN_RACE_PAGE);
    }
}
