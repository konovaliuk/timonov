package ua.timonov.web.project.command.horse;

import org.apache.log4j.Logger;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.parser.ParserFactory;
import ua.timonov.web.project.parser.Parser;
import ua.timonov.web.project.service.HorseService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.LoggerMessages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * adds new horse
 */
public class AddHorseAction extends GetHorsesAction {

    private static final Logger LOGGER = Logger.getLogger(AddHorseAction.class);

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseService horseService = serviceFactory.createHorseService();
    private Parser<Integer> integerParser = ParserFactory.createIntegerParser();
    private Horse horse;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        horse = createHorseFromRequest(request);
        horseService.validateNumberOfWonRaces(horse);
        horseService.save(horse);
        LOGGER.info(LoggerMessages.ADD_NEW_HORSE + horse.getName());
        request.setAttribute(Strings.MESSAGE_SUCCESS, true);
        return prepareHorsesPage(request);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        LOGGER.info(LoggerMessages.ERROR_ADD_NEW_HORSE + horse.getName());
        request.setAttribute(Strings.MESSAGE_ERROR, e.getMessage());
        request.setAttribute(Strings.ERROR_DETAILS, e.getCause());
        request.setAttribute(Strings.HORSE_WITH_INPUT_ERROR, horse);
        return prepareHorsesPage(request);
    }

    private Horse createHorseFromRequest(HttpServletRequest request) throws ParsingException {
        String name = request.getParameter(Strings.NAME);
        int year = integerParser.parse(request.getParameter(Strings.YEAR), Strings.YEAR);
        int totalRaces = integerParser.parse(request.getParameter(Strings.TOTAL_RACES), Strings.TOTAL_RACES);
        int wonRaces = integerParser.parse(request.getParameter(Strings.WON_RACES), Strings.WON_RACES);
        return new Horse(name, year, totalRaces, wonRaces);
    }
}