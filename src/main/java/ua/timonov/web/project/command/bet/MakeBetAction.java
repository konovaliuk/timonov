package ua.timonov.web.project.command.bet;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.bet.Odds;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.user.User;
import ua.timonov.web.project.parser.FactoryParser;
import ua.timonov.web.project.service.*;
import ua.timonov.web.project.util.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MakeBetAction extends Action {

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
            betService.makeBet(bet);
            request.setAttribute("messageSuccess", true);
        }
        catch (ServiceException e) {
            request.setAttribute("messageError", e.getMessage());
            request.setAttribute("errorDetails", e.getCause());
        }
        long horseInRaceId = bet.getOdds().getHorseInRaceId();
        Race race = raceService.findByHorseInRaceId(horseInRaceId);
        Horse horse = horseService.findByHorseInRaceId(horseInRaceId);
        request.setAttribute("bet", bet);
        request.setAttribute("race", race);
        request.setAttribute("horse", horse);
        return Pages.getPage(Pages.BET_SAVED_PAGE);
    }

    private Bet createBetFromRequest(HttpServletRequest request) {
        long userId =  FactoryParser.createIdParser().parse(request.getParameter("user"), "id");
        long oddsId = Long.valueOf(request.getParameter("oddsId"));
        Odds odds = oddsService.findById(oddsId);
        Double betSum = Double.valueOf(request.getParameter("sum"));
        User user = userService.findById(userId);
        return new Bet.Builder(user, odds)
                .money(betSum)
                .build();
    }
}
