package ua.timonov.web.project.command.race;

import org.apache.log4j.Logger;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.parser.Parser;
import ua.timonov.web.project.parser.ParserFactory;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.LoggerMessages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * saves finish places
 */
public class SaveRacePlacesAction extends ManageRaceAction {

    private static final Logger LOGGER = Logger.getLogger(SaveRacePlacesAction.class);

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();
    private Parser<Long> idParser = ParserFactory.createIdParser();
    private Parser<Integer> intParser = ParserFactory.createIntegerParser();
    List<Integer> inputtedPlaces = new ArrayList<>();
    private Race race;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ParsingException {
        Long raceId = idParser.parse(request.getParameter(Strings.RACE_ID), Strings.RACE_ID);
        race = raceService.findById(raceId);
        List<HorseInRace> listOfHorsesInRace = horseInRaceService.findListByRaceId(raceId);
        inputtedPlaces.clear();
        for (int i = 0; i < listOfHorsesInRace.size(); i++) {
            inputtedPlaces.add(intParser.parse(request.getParameter(Strings.PLACES + i), Strings.PLACES));
        }
        raceService.saveInputtedPlaces(listOfHorsesInRace, inputtedPlaces);
        LOGGER.info(LoggerMessages.FINISH_PLACES_SAVED);
        request.setAttribute(Strings.MESSAGE_SUCCESS, true);
        request.setAttribute(Strings.INPUTTED_PLACES, inputtedPlaces);
        return prepareManageRacePage(request, race);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        LOGGER.info(LoggerMessages.ERROR_SETTING_FINISH_PLACES);
        request.setAttribute(Strings.MESSAGE_ERROR, e.getMessage());
        request.setAttribute(Strings.ERROR_DETAILS, e.getStackTrace());
        request.setAttribute(Strings.INPUTTED_PLACES, inputtedPlaces);
        return prepareManageRacePage(request, race);
    }
}
