package ua.timonov.web.project.command.horse;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.service.HorseService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditHorseAction extends Action {

    public static final String HORSE_EDIT = "horseEdit";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseService horseService = serviceFactory.createHorseService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        long horseId = Long.valueOf(request.getParameter("horseId"));
        Horse horse = horseService.findById(horseId);
        return prepareEditHorsePage(request, horse);
    }

    private String prepareEditHorsePage(HttpServletRequest request, Horse horse) {
        request.setAttribute("horse", horse);
        return CONFIG.getString(HORSE_EDIT);
    }
}
