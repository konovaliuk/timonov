package ua.timonov.web.project.command.bet;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.AppException;
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

public class MakeBetAction implements Action {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseService horseService = serviceFactory.createHorseService();
    private RaceService raceService = serviceFactory.createRaceService();
    private OddsService oddsService = serviceFactory.createOddsService();
    private UserService userService = serviceFactory.createUserService();
    private BetService betService = serviceFactory.createBetService();
    private Race race;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Bet bet = createBetFromRequest(request);
        Long horseInRaceId = bet.getOdds().getHorseInRaceId();
        race = raceService.findByHorseInRaceId(horseInRaceId);
        Horse horse = horseService.findByHorseInRaceId(horseInRaceId);

        betService.makeBet(bet);
        request.setAttribute("messageSuccess", true);

        request.setAttribute("bet", bet);
        request.setAttribute("race", race);
        request.setAttribute("horse", horse);
        return Pages.getPage(Pages.BET_SAVED_PAGE);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        request.setAttribute("messageError", e.getMessage());
        request.setAttribute("errorDetails", e.getCause());
        request.setAttribute("race", race);
        return Pages.getPage(Pages.RACE_PAGE);
    }

    private Bet createBetFromRequest(HttpServletRequest request) throws AppException {
        Long userId =  FactoryParser.createIdParser().parse(request.getParameter("user"), "id");
        Long oddsId = Long.valueOf(request.getParameter("oddsId"));
        Odds odds = oddsService.findById(oddsId);
        Double betSum = Double.valueOf(request.getParameter("sum"));
        User user = userService.findById(userId);
        return new Bet.Builder(user, odds)
                .money(betSum)
                .build();
    }
}
