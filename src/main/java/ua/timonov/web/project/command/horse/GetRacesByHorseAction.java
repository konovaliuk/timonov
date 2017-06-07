package ua.timonov.web.project.command.horse;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.parser.ParserFactory;
import ua.timonov.web.project.parser.Parser;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.HorseService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.Pages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * finds all races with chosen horse
 */
public class GetRacesByHorseAction implements Action {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseService horseService = serviceFactory.createHorseService();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();
    private RaceService raceService = serviceFactory.createRaceService();
    private Parser<Long> idParser = ParserFactory.createIdParser();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Long horseId = idParser.parse(request.getParameter(Strings.HORSE_ID), Strings.HORSE_ID);
        Horse horse = horseService.findById(horseId);
        return prepareEditHorsePage(request, horse);
    }

    private String prepareEditHorsePage(HttpServletRequest request, Horse horse) throws ServiceException {
        List<HorseInRace> listHorsesInRace = horseInRaceService.findListByHorseId(horse.getId());
        List<Race> races = new ArrayList<>();
        for (HorseInRace horseInRace : listHorsesInRace) {
            races.add(raceService.findByHorseInRaceId(horseInRace.getId()));
        }
        request.setAttribute(Strings.HORSE, horse);
        request.setAttribute(Strings.LIST_HORSES_IN_RACE, listHorsesInRace);
        request.setAttribute(Strings.RACES, races);
        return Pages.getPage(Pages.RACES_WITH_HORSE_PAGE);
    }
}
