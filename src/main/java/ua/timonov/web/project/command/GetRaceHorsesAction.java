package ua.timonov.web.project.command;

import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.RaceService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetRaceHorsesAction extends Action {

    public static final String RACE_PAGE = "/WEB-INF/jsp/race.jsp";

    private RaceService raceService = new RaceService();
    private HorseInRaceService horseInRaceService = new HorseInRaceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long raceId = Long.valueOf(request.getParameter("id"));
        request.setAttribute("race", raceService.getById(raceId));
        request.setAttribute("horsesInRace", horseInRaceService.getByRace(raceId));
        return RACE_PAGE;
    }
}
