package ua.timonov.web.project.command.horse;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.service.HorseService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetHorsesAction extends Action {
    public static final String HORSES_PAGE = "horses";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseService horseService = serviceFactory.createHorseService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return prepareRacesPage(request);
    }

    protected String prepareRacesPage(HttpServletRequest request) {
        request.setAttribute("horses", horseService.findAll());
        return CONFIG.getString(HORSES_PAGE);
    }
}
