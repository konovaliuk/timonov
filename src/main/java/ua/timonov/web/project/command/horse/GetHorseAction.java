package ua.timonov.web.project.command.horse;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.service.HorseService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetHorseAction extends Action {

    public static final String HORSE_PAGE = "/WEB-INF/jsp/horse.jsp";

    private HorseService horseService = ServiceFactory.getInstance().createHorseService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long horseId = Long.valueOf(request.getParameter("horseId"));
        request.setAttribute("horse", horseService.findById(horseId));
        return HORSE_PAGE;
    }
}
