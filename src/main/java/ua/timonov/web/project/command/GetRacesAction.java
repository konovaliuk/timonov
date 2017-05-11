package ua.timonov.web.project.command;

import ua.timonov.web.project.service.RaceService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetRacesAction extends Action {

    public static final String RACES_PAGE = "/WEB-INF/jsp/races.jsp";

    private RaceService raceService = new RaceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("races", raceService.getAll());
        return RACES_PAGE;
    }
}
