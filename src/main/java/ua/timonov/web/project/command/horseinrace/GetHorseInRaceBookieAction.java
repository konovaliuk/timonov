package ua.timonov.web.project.command.horseinrace;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.bet.BetType;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetHorseInRaceBookieAction implements Action {

    private HorseInRaceService horseInRaceService = ServiceFactory.getInstance().createHorseInRaceService();
    private RaceService raceService = ServiceFactory.getInstance().createRaceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        long horseInRaceId = Long.valueOf(request.getParameter("horseInRaceId"));
        return prepareHorseInRacePage(request, horseInRaceId);
    }

    protected String prepareHorseInRacePage(HttpServletRequest request, long horseInRaceId) throws ServiceException {
        request.setAttribute("horseInRace", horseInRaceService.findById(horseInRaceId));
        request.setAttribute("race", raceService.findByHorseInRaceId(horseInRaceId));
        request.setAttribute("betTypes", BetType.values());
        return Pages.getPage(Pages.HORSE_IN_RACE_BOOKIE_PAGE);
    }
}
