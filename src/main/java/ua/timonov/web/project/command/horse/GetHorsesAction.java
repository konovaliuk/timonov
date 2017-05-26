package ua.timonov.web.project.command.horse;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.service.HorseService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetHorsesAction extends Action {
    public static final String HORSES_PAGE = "/WEB-INF/jsp/horses.jsp";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseService horseService = serviceFactory.createHorseService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("horses", horseService.findAll());
        return HORSES_PAGE;
    }
}
