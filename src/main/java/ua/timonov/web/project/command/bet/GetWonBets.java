package ua.timonov.web.project.command.bet;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.Pages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * finds all won bets for current race
 */
public class GetWonBets implements Action {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        long raceId = Long.valueOf(request.getParameter(Strings.RACE_ID));
        Race race = raceService.findById(raceId);

        return prepareWonBetsPage(request, race);
    }

    private String prepareWonBetsPage(HttpServletRequest request, Race race) throws ServiceException {
        List<Bet> wonBets = raceService.findWonBetsByRaceId(race.getId());
        List<HorseInRace> listBetHorses = horseInRaceService.findBetHorsesInRace(wonBets);
        request.setAttribute(Strings.RACE, race);
        request.setAttribute(Strings.WON_BETS, wonBets);
        request.setAttribute(Strings.LIST_BET_HORSES, listBetHorses);
        return Pages.getPage(Pages.WON_BETS_PAGE);
    }
}
