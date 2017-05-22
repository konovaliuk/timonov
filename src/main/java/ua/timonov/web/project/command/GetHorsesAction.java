package ua.timonov.web.project.command;

import ua.timonov.web.project.service.HorseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetHorsesAction extends Action {
    public static final String HORSES_PAGE = "/WEB-INF/jsp/horses.jsp";

    private HorseService horseService = new HorseService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("horses", horseService.findAll());
        return HORSES_PAGE;
    }
}
