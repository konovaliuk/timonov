package ua.timonov.web.project.command.user;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.bet.BetStatus;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.user.User;
import ua.timonov.web.project.parser.Parser;
import ua.timonov.web.project.parser.ParserFactory;
import ua.timonov.web.project.service.*;
import ua.timonov.web.project.util.Pages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * prepares page with user bets
 */
public class GetUserBetsAction implements Action {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private BetService betService = serviceFactory.createBetService();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();
    private RaceService raceService = serviceFactory.createRaceService();
    private UserService userService = serviceFactory.createUserService();
    private Parser<Long> idParser = ParserFactory.createIdParser();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        return prepareUserBetsPage(request);
    }

    protected String prepareUserBetsPage(HttpServletRequest request) throws AppException {
        Long userId = idParser.parse(request.getParameter(Strings.USER_ID), Strings.USER_ID);
        User user = userService.findById(userId);
        List<Bet> userBets = betService.findListByUser(user.getId());
        List<HorseInRace> listBetHorses = horseInRaceService.findBetHorsesInRace(userBets);
        List<Race> listBetRaces = raceService.findBetRaces(userBets);
        request.setAttribute(Strings.USER, user);
        request.setAttribute(Strings.USER_BETS, userBets);
        request.setAttribute(Strings.LIST_BET_HORSES, listBetHorses);
        request.setAttribute(Strings.LIST_BET_RACES, listBetRaces);
        request.setAttribute(Strings.BET_STATUS_MAID, BetStatus.MADE);
        request.setAttribute(Strings.BET_STATUS_PAID, BetStatus.PAID);
        return Pages.getPage(Pages.USER_BETS_PAGE);
    }
}
