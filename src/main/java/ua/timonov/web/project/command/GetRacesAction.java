package ua.timonov.web.project.command;

import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetRacesAction extends Action {

    public static final String RACES_PAGE = "/WEB-INF/jsp/races.jsp";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("races", raceService.findAll());
        return RACES_PAGE;
    }
}
