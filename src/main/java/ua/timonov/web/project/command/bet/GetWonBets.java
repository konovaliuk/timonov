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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetWonBets extends Action {

    public static final String WON_BETS = "wonBets";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        long raceId = Long.valueOf(request.getParameter("raceId"));
        Race race = raceService.findById(raceId);
        return prepareWonBetsPage(request, race);
    }

    private String prepareWonBetsPage(HttpServletRequest request, Race race) {
        List<Bet> wonBets = raceService.findWonBetsByRaceId(race.getId());
        List<HorseInRace> listBetHorses = horseInRaceService.findBetHorsesInRace(wonBets);
        request.setAttribute("race", race);
        request.setAttribute("wonBets", wonBets);
        request.setAttribute("listBetHorses", listBetHorses);
        return CONFIG.getString(WON_BETS);
    }
}
