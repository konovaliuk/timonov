package ua.timonov.web.project.command.odds;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.bet.BetType;
import ua.timonov.web.project.model.bet.Odds;
import ua.timonov.web.project.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveOddsAction extends Action {

    public static final String HORSE_IN_RACE_BOOKIE = "horseInRaceBookie";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();
    private RaceService raceService = serviceFactory.createRaceService();
    private OddsService oddsService = serviceFactory.createOddsService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        Odds odds = createOddsFromRequest(request);
        oddsService.save(odds);
        request.setAttribute("horseInRace", horseInRaceService.findById(odds.getHorseInRaceId()));
        request.setAttribute("race", raceService.findByHorseInRaceId(odds.getHorseInRaceId()));
        request.setAttribute("betTypes", BetType.values());
        return CONFIG.getString(HORSE_IN_RACE_BOOKIE);
    }

    private Odds createOddsFromRequest(HttpServletRequest request) {
        String parameterId = request.getParameter("oddsId");
        long id = parameterId != null ? Long.valueOf(parameterId) : 0;
        long horseInRaceId = Long.valueOf(request.getParameter("horseInRaceId"));
        BetType betType = BetType.valueOf(request.getParameter("betType"));
        int total = Integer.valueOf(request.getParameter("total"));
        int chances = Integer.valueOf(request.getParameter("chances"));
        return new Odds(id, horseInRaceId, betType, total, chances);
    }
}
