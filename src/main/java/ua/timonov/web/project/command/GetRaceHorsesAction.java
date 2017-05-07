package ua.timonov.web.project.command;

import ua.timonov.web.project.service.HorseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetRaceHorsesAction extends Action {

    public static final String RACE_PAGE = "/WEB-INF/jsp/race.jsp";

    private HorseService horseService = new HorseService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("horses", horseService.getAll());
        return RACE_PAGE;
    }
}
