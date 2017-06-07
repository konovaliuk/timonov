package ua.timonov.web.project.command.user;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.bet.BetStatus;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.user.User;
import ua.timonov.web.project.service.*;
import ua.timonov.web.project.util.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetUserBetsAction implements Action {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private BetService betService = serviceFactory.createBetService();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();
    private RaceService raceService = serviceFactory.createRaceService();
    private UserService userService = serviceFactory.createUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        return prepareUserBetsPage(request);
    }

    protected String prepareUserBetsPage(HttpServletRequest request) throws ServiceException {
        Long userId = Long.valueOf(request.getParameter("userId"));
        User user = userService.findById(userId);
        List<Bet> userBets = betService.findListByUser(user.getId());
        List<HorseInRace> listBetHorses = horseInRaceService.findBetHorsesInRace(userBets);
        List<Race> listBetRaces = raceService.findBetRaces(userBets);
        request.setAttribute("user", user);
        request.setAttribute("userBets", userBets);
        request.setAttribute("listBetHorses", listBetHorses);
        request.setAttribute("listBetRaces", listBetRaces);
        request.setAttribute("betStatusMaid", BetStatus.MADE);
        request.setAttribute("betStatusPaid", BetStatus.PAID);
        return Pages.getPage(Pages.USER_BETS_PAGE);
    }
}
