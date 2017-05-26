package ua.timonov.web.project.command.horseinrace;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetHorseInRaceAction extends Action {

    public static final String HORSE_IN_RACE_PAGE = "/WEB-INF/jsp/horseInRace.jsp";

    private HorseInRaceService horseInRaceService = ServiceFactory.getInstance().createHorseInRaceService();
    private RaceService raceService = ServiceFactory.getInstance().createRaceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long id = Long.valueOf(request.getParameter("horseInRaceId"));
        request.setAttribute("horseInRace", horseInRaceService.findById(id));
        request.setAttribute("race", raceService.findByHorseInRaceId(id));
        return HORSE_IN_RACE_PAGE;
    }
}
