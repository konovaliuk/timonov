package ua.timonov.web.project.command;

import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.bet.BetType;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.user.User;
import ua.timonov.web.project.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MakeBetAction extends Action {

    public static final String BET_SAVED_PAGE = "/WEB-INF/jsp/bet/save.jsp";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();
    private RaceService raceService = serviceFactory.createRaceService();
    private OddsService oddsService = serviceFactory.createOddsService();
    private UserService userService = serviceFactory.createUserService();
    private BetService betService = serviceFactory.createBetService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        Bet bet = createBetFromRequest(request);
        betService.save(bet);
        request.setAttribute("bet", bet);
        request.setAttribute("oddsId", request.getParameter("odds"));
        request.setAttribute("race", raceService.getByHorseInRaceId(bet.getHorseInRace().getId()));
        return BET_SAVED_PAGE;
    }

    private Bet createBetFromRequest(HttpServletRequest request) {
        long oddsId = Long.valueOf(request.getParameter("odds"));
        long horseInRaceId = Long.valueOf(request.getParameter("horse_in_race"));
        double betSum = Double.valueOf(request.getParameter("sum"));

        BetType betType = oddsService.findById(oddsId).getBetType();
        HorseInRace horseInRace = horseInRaceService.findById(horseInRaceId);
        // TODO
        long userId = 6; // Long.valueOf(request.getParameter("userId"));
        User user = userService.findById(userId);

        return new Bet(user, betType, horseInRace, betSum);
    }
}
