package ua.timonov.web.project.command.horseinrace;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.model.bet.BetType;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetHorseInRaceBookieAction extends Action {

    public static final String HORSE_IN_RACE_BOOKIE = "horseInRaceBookie";

    private HorseInRaceService horseInRaceService = ServiceFactory.getInstance().createHorseInRaceService();
    private RaceService raceService = ServiceFactory.getInstance().createRaceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long horseInRaceId = Long.valueOf(request.getParameter("horseInRaceId"));
        request.setAttribute("horseInRace", horseInRaceService.findById(horseInRaceId));
        request.setAttribute("race", raceService.findByHorseInRaceId(horseInRaceId));
        request.setAttribute("betTypes", BetType.values());
        return CONFIG.getString(HORSE_IN_RACE_BOOKIE);
    }
}
