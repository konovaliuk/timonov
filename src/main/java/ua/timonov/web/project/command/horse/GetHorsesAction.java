package ua.timonov.web.project.command.horse;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.service.HorseService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetHorsesAction implements Action {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseService horseService = serviceFactory.createHorseService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        return prepareRacesPage(request);
    }

    protected String prepareRacesPage(HttpServletRequest request) throws ServiceException {
        request.setAttribute("horses", horseService.findAll());
        return Pages.getPage(Pages.HORSES_PAGE);
    }
}
