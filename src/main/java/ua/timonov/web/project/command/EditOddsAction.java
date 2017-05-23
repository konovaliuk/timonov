package ua.timonov.web.project.command;

import ua.timonov.web.project.model.bet.BetType;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.OddsService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditOddsAction extends Action {

    private HorseInRaceService horseInRaceService = ServiceFactory.getInstance().createHorseInRaceService();
    private RaceService raceService = ServiceFactory.getInstance().createRaceService();
    private OddsService oddsService = ServiceFactory.getInstance().createOddsService();

    public static final String HORSE_IN_RACE_BOOKIE_PAGE = "/WEB-INF/jsp/odds/edit.jsp";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long oddsId = Long.valueOf(request.getParameter("id"));
        long horseInRaceId = Long.valueOf(request.getParameter("horseInRace"));
        request.setAttribute("horseInRace", horseInRaceService.getById(horseInRaceId));
        request.setAttribute("race", raceService.getByHorseInRaceId(horseInRaceId));
        request.setAttribute("betTypes", BetType.values());
        request.setAttribute("odds", oddsService.getById(oddsId));
        return HORSE_IN_RACE_BOOKIE_PAGE ;
    }
}
