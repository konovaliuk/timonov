package ua.timonov.web.project.command.bet;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.bet.Odds;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.user.User;
import ua.timonov.web.project.parser.FactoryParser;
import ua.timonov.web.project.parser.Parser;
import ua.timonov.web.project.service.*;
import ua.timonov.web.project.util.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MakeBetAction implements Action {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseService horseService = serviceFactory.createHorseService();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();
    private RaceService raceService = serviceFactory.createRaceService();
    private OddsService oddsService = serviceFactory.createOddsService();
    private BetService betService = serviceFactory.createBetService();
    private Race race;
    private Long horseInRaceId;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Bet bet = createBetFromRequest(request);
        horseInRaceId = bet.getOdds().getHorseInRaceId();
        race = raceService.findByHorseInRaceId(horseInRaceId);
        Horse horse = horseService.findByHorseInRaceId(horseInRaceId);
        betService.makeBet(bet, race);
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
        /*request.setAttribute("race", race);
        return Pages.getPage(Pages.RACE_PAGE);*/
        request.setAttribute("horseInRace", horseInRaceService.findById(horseInRaceId));
        request.setAttribute("race", race);
        return Pages.getPage(Pages.HORSE_IN_RACE_PAGE);
    }

    private Bet createBetFromRequest(HttpServletRequest request) throws AppException {
        Parser<Long> idParser = FactoryParser.createIdParser();
        Long oddsId = idParser.parse(request.getParameter("oddsId"), "oddsId");
        Odds odds = oddsService.findById(oddsId);
        User user = (User) request.getSession().getAttribute("loggedUser");
        Double betSum = Double.valueOf(request.getParameter("sum"));
        return new Bet.Builder(user, odds)
                .money(betSum)
                .build();
    }
}
