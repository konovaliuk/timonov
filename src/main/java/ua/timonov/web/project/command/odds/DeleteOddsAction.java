package ua.timonov.web.project.command.odds;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.model.bet.BetType;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.OddsService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteOddsAction extends Action {

    public static final String HORSE_IN_RACE_BOOKIE = "horseInRaceBookie";

    private HorseInRaceService horseInRaceService = ServiceFactory.getInstance().createHorseInRaceService();
    private RaceService raceService = ServiceFactory.getInstance().createRaceService();
    private OddsService oddsService = ServiceFactory.getInstance().createOddsService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long oddsId = Long.valueOf(request.getParameter("oddsId"));
        long horseInRaceId = Long.valueOf(request.getParameter("horseInRaceId"));
        oddsService.delete(oddsId);
        request.setAttribute("horseInRace", horseInRaceService.findById(horseInRaceId));
        request.setAttribute("race", raceService.findByHorseInRaceId(horseInRaceId));
        request.setAttribute("betTypes", BetType.values());
        return CONFIG.getString(HORSE_IN_RACE_BOOKIE);
    }
}