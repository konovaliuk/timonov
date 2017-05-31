package ua.timonov.web.project.command.bet;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.bet.Odds;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.user.User;
import ua.timonov.web.project.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public class MakeBetAction extends Action {

    public static final String BET_SAVED = "betSaved";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseService horseService = serviceFactory.createHorseService();
    private RaceService raceService = serviceFactory.createRaceService();
    private OddsService oddsService = serviceFactory.createOddsService();
    private UserService userService = serviceFactory.createUserService();
    private BetService betService = serviceFactory.createBetService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        Bet bet = createBetFromRequest(request);
        try {
            betService.tryToMakeBet(bet);
            request.setAttribute("messageSuccess", true);
        }
        catch (ServiceException e) {
            request.setAttribute("messageError", e.getMessage());
            request.setAttribute("errorDetails", e.getCause());
        }

        long horseInRaceId = bet.getOdds().getHorseInRaceId();
        Race race = raceService.findByHorseInRaceId(horseInRaceId);
        Horse horse = horseService.findByHorseIbRaceId(horseInRaceId);
        request.setAttribute("bet", bet);
        request.setAttribute("race", race);
        request.setAttribute("horse", horse);
        return CONFIG.getString(BET_SAVED);
    }

    private Bet createBetFromRequest(HttpServletRequest request) {
        long oddsId = Long.valueOf(request.getParameter("oddsId"));
        Odds odds = oddsService.findById(oddsId);
        BigDecimal betSum = BigDecimal.valueOf(Double.valueOf(request.getParameter("sum")));
        // TODO
        long userId = 6; // Long.valueOf(request.getParameter("userId"));
        User user = userService.findById(userId);

        return new Bet(user, odds, betSum);
    }
}
