package ua.timonov.web.project.command.odds;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.bet.BetType;
import ua.timonov.web.project.model.bet.Odds;
import ua.timonov.web.project.parser.Parser;
import ua.timonov.web.project.parser.ParserFactory;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.OddsService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.Pages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * finds bet odds for editing
 */
public class EditOddsAction implements Action {

    private HorseInRaceService horseInRaceService = ServiceFactory.getInstance().createHorseInRaceService();
    private RaceService raceService = ServiceFactory.getInstance().createRaceService();
    private OddsService oddsService = ServiceFactory.getInstance().createOddsService();
    private Parser<Long> idParser = ParserFactory.createIdParser();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Long oddsId = idParser.parse(request.getParameter(Strings.ODDS_ID), Strings.ODDS_ID);
        Odds odds = oddsService.findById(oddsId);
        return prepareOddsEditPage(request, odds);
    }

    protected String prepareOddsEditPage(HttpServletRequest request, Odds odds) throws ServiceException {
        request.setAttribute(Strings.ODDS, odds);
        request.setAttribute(Strings.HORSE_IN_RACE, horseInRaceService.findById(odds.getHorseInRaceId()));
        request.setAttribute(Strings.RACE, raceService.findByHorseInRaceId(odds.getHorseInRaceId()));
        request.setAttribute(Strings.BET_TYPES, BetType.values());
        return Pages.getPage(Pages.ODDS_EDIT_PAGE);
    }
}