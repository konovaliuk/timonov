package ua.timonov.web.project.command;

import ua.timonov.web.project.service.HorseInRaceService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetHorseInRaceAction extends Action {

    public static final String HORSE_IN_RACE_PAGE = "/WEB-INF/jsp/horse_in_race.jsp";

    private HorseInRaceService horseInRaceService = new HorseInRaceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long id = Long.valueOf(request.getParameter("id"));
        request.setAttribute("horseInRace", horseInRaceService.getById(id));
        return HORSE_IN_RACE_PAGE;
    }
}
