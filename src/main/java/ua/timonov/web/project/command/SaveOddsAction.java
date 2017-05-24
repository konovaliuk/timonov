package ua.timonov.web.project.command;

import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.bet.BetType;
import ua.timonov.web.project.model.bet.Odds;
import ua.timonov.web.project.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveOddsAction extends Action {

    public static final String HORSE_IN_RACE_BOOKIE_PAGE = "/WEB-INF/jsp/horseInRaceBookie.jsp";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();
    private RaceService raceService = serviceFactory.createRaceService();
    private OddsService oddsService = serviceFactory.createOddsService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        Odds odds = createOddsFromRequest(request);
        long horseInRaceId = Long.valueOf(request.getParameter("horseInRace"));
        // TODO what if there is such odds in DB
        oddsService.save(odds, horseInRaceId);

        request.setAttribute("horseInRace", horseInRaceService.findById(horseInRaceId));
        request.setAttribute("race", raceService.getByHorseInRaceId(horseInRaceId));
        request.setAttribute("betTypes", BetType.values());
        return HORSE_IN_RACE_BOOKIE_PAGE ;
    }

    private Odds createOddsFromRequest(HttpServletRequest request) {
        String parameterId = request.getParameter("id");
        long id = parameterId != null ? Long.valueOf(parameterId) : 0;
        BetType betType = BetType.valueOf(request.getParameter("betType"));
        int total = Integer.valueOf(request.getParameter("total"));
        int chances = Integer.valueOf(request.getParameter("chances"));
        return new Odds(id, betType, total, chances);
    }
}
