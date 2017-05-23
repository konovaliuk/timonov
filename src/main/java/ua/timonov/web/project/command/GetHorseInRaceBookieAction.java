package ua.timonov.web.project.command;

import ua.timonov.web.project.model.bet.BetType;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetHorseInRaceBookieAction extends Action {

    public static final String HORSE_IN_RACE_BOOKIE_PAGE = "/WEB-INF/jsp/horseInRaceBookie.jsp";

    private HorseInRaceService horseInRaceService = ServiceFactory.getInstance().createHorseInRaceService();
    private RaceService raceService = ServiceFactory.getInstance().createRaceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long id = Long.valueOf(request.getParameter("horseInRace"));
        request.setAttribute("horseInRace", horseInRaceService.getById(id));
        request.setAttribute("race", raceService.getByHorseInRaceId(id));
        request.setAttribute("betTypes", BetType.values());
        return HORSE_IN_RACE_BOOKIE_PAGE;
    }
}
