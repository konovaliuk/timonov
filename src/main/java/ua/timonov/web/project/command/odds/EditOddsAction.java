package ua.timonov.web.project.command.odds;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.model.bet.BetType;
import ua.timonov.web.project.model.bet.Odds;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.OddsService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditOddsAction extends Action {

    private HorseInRaceService horseInRaceService = ServiceFactory.getInstance().createHorseInRaceService();
    private RaceService raceService = ServiceFactory.getInstance().createRaceService();
    private OddsService oddsService = ServiceFactory.getInstance().createOddsService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long oddsId = Long.valueOf(request.getParameter("oddsId"));
        Odds odds = oddsService.findById(oddsId);
        return prepareOddsEditPage(request, odds);
    }

    protected String prepareOddsEditPage(HttpServletRequest request, Odds odds) {
        request.setAttribute("odds", odds);
        request.setAttribute("horseInRace", horseInRaceService.findById(odds.getHorseInRaceId()));
        request.setAttribute("race", raceService.findByHorseInRaceId(odds.getHorseInRaceId()));
        request.setAttribute("betTypes", BetType.values());
        return Pages.getPage(Pages.ODDS_EDIT_PAGE);
    }
}