package ua.timonov.web.project.command.bet;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
        List<HorseInRace> listBetHorses = new ArrayList<>();
        for (Bet wonBet : wonBets) {
            long horseInRaceId = wonBet.getOdds().getHorseInRaceId();
            HorseInRace horseInRace = horseInRaceService.findById(horseInRaceId);
            listBetHorses.add(horseInRace);
        }
        request.setAttribute("race", race);
        request.setAttribute("wonBets", wonBets);
        request.setAttribute("listHorsesInRace", listBetHorses);
        return CONFIG.getString(WON_BETS);
    }
}
