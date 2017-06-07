package ua.timonov.web.project.command.horse;


import org.apache.log4j.Logger;
import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.parser.ParserFactory;
import ua.timonov.web.project.parser.Parser;
import ua.timonov.web.project.service.HorseService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.LoggerMessages;
import ua.timonov.web.project.util.Pages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * saves edited horse
 */
public class SaveEditedHorseAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(SaveEditedHorseAction.class);

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseService horseService = serviceFactory.createHorseService();
    private Parser<Long> idParser = ParserFactory.createIdParser();
    private Horse horse;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        horse = createHorseFromRequest(request);
        horseService.validateNumberOfWonRaces(horse);
        horseService.save(horse);
        LOGGER.info(LoggerMessages.SAVED_EDITED_HORSE + horse.getName());
        request.setAttribute(Strings.MESSAGE_SUCCESS, true);
        request.setAttribute(Strings.HORSE, horse);
        return Pages.getPage(Pages.HORSE_EDIT_PAGE);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        LOGGER.warn(LoggerMessages.ERROR_EDITING_HORSE + horse.getName());
        request.setAttribute(Strings.MESSAGE_ERROR, e.getMessage());
        request.setAttribute(Strings.ERROR_DETAILS, e.getCause());
        request.setAttribute(Strings.HORSE, horse);
        return Pages.getPage(Pages.HORSE_EDIT_PAGE);
    }

    private Horse createHorseFromRequest(HttpServletRequest request) throws ParsingException {
        long id = idParser.parse(request.getParameter(Strings.HORSE_ID), Strings.HORSE_ID);
        String name = request.getParameter(Strings.NAME);
        int year = Integer.valueOf(request.getParameter(Strings.YEAR));
        int totalRaces = Integer.valueOf(request.getParameter(Strings.TOTAL_RACES));
        int wonRaces = Integer.valueOf(request.getParameter(Strings.WON_RACES));
        return new Horse(id, name, year, totalRaces, wonRaces);
    }
}
