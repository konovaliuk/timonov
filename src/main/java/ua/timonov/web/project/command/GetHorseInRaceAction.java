package ua.timonov.web.project.command;

import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetHorseInRaceAction extends Action {

    public static final String HORSE_IN_RACE_PAGE = "/WEB-INF/jsp/horseInRace.jsp";

    private HorseInRaceService horseInRaceService = ServiceFactory.getInstance().getHorseInRaceService();
    private RaceService raceService = ServiceFactory.getInstance().getRaceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long id = Long.valueOf(request.getParameter("id"));
        request.setAttribute("horseInRace", horseInRaceService.getById(id));
        request.setAttribute("race", raceService.getByHorseInRaceId(id));
        return HORSE_IN_RACE_PAGE;
    }
}