package ua.timonov.web.project.command;

import ua.timonov.web.project.model.bet.BetType;
import ua.timonov.web.project.model.bet.Odds;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.OddsService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveOddsAction extends Action {

    public static final String HORSE_IN_RACE_BOOKIE_PAGE = "/WEB-INF/jsp/horseInRaceBookie.jsp";

    private HorseInRaceService horseInRaceService = ServiceFactory.getInstance().getHorseInRaceService();
    private RaceService raceService = ServiceFactory.getInstance().getRaceService();
    private OddsService oddsService = ServiceFactory.getInstance().getOddsService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Odds odds = createOddsFromRequest(request);
        long horseInRaceId = Long.valueOf(request.getParameter("horseInRace"));
        // TODO what if there is such odds in DB
        oddsService.save(horseInRaceId, odds);

        request.setAttribute("horseInRace", horseInRaceService.getById(horseInRaceId));
        request.setAttribute("race", raceService.getByHorseInRaceId(horseInRaceId));
        request.setAttribute("betTypes", BetType.values());
        return HORSE_IN_RACE_BOOKIE_PAGE ;
    }

    private Odds createOddsFromRequest(HttpServletRequest request) {
        BetType betType = BetType.valueOf(request.getParameter("betType"));
        int total = Integer.valueOf(request.getParameter("total"));
        int chances = Integer.valueOf(request.getParameter("chances"));
        String parameterId = request.getParameter("id");
        long id = 0;
        if (parameterId != null) {
            id = Long.valueOf(parameterId);
        }
        return new Odds(id, betType, total, chances);
    }
}
