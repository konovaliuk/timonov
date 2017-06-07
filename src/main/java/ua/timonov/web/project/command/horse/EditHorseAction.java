package ua.timonov.web.project.command.horse;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.parser.ParserFactory;
import ua.timonov.web.project.parser.Parser;
import ua.timonov.web.project.service.HorseService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.Pages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * prepares page for editing horse
 */
public class EditHorseAction implements Action {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseService horseService = serviceFactory.createHorseService();
    private Parser<Long> idParser = ParserFactory.createIdParser();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        Long horseId = idParser.parse(request.getParameter(Strings.HORSE_ID), Strings.HORSE_ID);
        Horse horse = horseService.findById(horseId);
        return prepareEditHorsePage(request, horse);
    }

    private String prepareEditHorsePage(HttpServletRequest request, Horse horse) {
        request.setAttribute(Strings.HORSE, horse);
        return Pages.getPage(Pages.HORSE_EDIT_PAGE);
    }
}
