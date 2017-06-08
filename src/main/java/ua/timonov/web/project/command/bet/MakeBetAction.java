package ua.timonov.web.project.command.bet;

import org.apache.log4j.Logger;
import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.bet.Odds;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.user.User;
import ua.timonov.web.project.parser.ParserFactory;
import ua.timonov.web.project.parser.Parser;
import ua.timonov.web.project.service.*;
import ua.timonov.web.project.util.LoggerMessages;
import ua.timonov.web.project.util.Pages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * creates new bet
 */
public class MakeBetAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(MakeBetAction.class);

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
        request.setAttribute(Strings.MESSAGE_SUCCESS, true);
        LOGGER.info(LoggerMessages.BET_MADE);
        request.setAttribute(Strings.BET, bet);
        request.setAttribute(Strings.RACE, race);
        request.setAttribute(Strings.HORSE, horse);
        return Pages.getPage(Pages.BET_SAVED_PAGE);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        LOGGER.info(LoggerMessages.ERROR_MAKE_BET);
        request.setAttribute(Strings.MESSAGE_ERROR, e.getMessage());
        request.setAttribute(Strings.ERROR_DETAILS, e.getStackTrace());
        request.setAttribute(Strings.HORSE_IN_RACE, horseInRaceService.findById(horseInRaceId));
        request.setAttribute(Strings.RACE, race);
        return Pages.getPage(Pages.HORSE_IN_RACE_PAGE);
    }

    private Bet createBetFromRequest(HttpServletRequest request) throws AppException {
        Parser<Long> idParser = ParserFactory.createIdParser();
        Long oddsId = idParser.parse(request.getParameter(Strings.ODDS_ID), Strings.ODDS_ID);
        Odds odds = oddsService.findById(oddsId);
        User user = (User) request.getSession().getAttribute(Strings.LOGGED_USER);
        Double betSum = Double.valueOf(request.getParameter(Strings.SUM));
        return new Bet.Builder(user, odds)
                .money(betSum)
                .build();
    }
}
