package ua.timonov.web.project.command.horse;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.HorseService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class GetRacesByHorseAction implements Action {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseService horseService = serviceFactory.createHorseService();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();
    private RaceService raceService = serviceFactory.createRaceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        Long horseId = Long.valueOf(request.getParameter("horseId"));
        Horse horse = horseService.findById(horseId);
        return prepareEditHorsePage(request, horse);
    }

    private String prepareEditHorsePage(HttpServletRequest request, Horse horse) throws ServiceException {
        List<HorseInRace> listHorsesInRace = horseInRaceService.findListByHorseId(horse.getId());
        List<Race> races = new ArrayList<>();
        for (HorseInRace horseInRace : listHorsesInRace) {
            races.add(raceService.findByHorseInRaceId(horseInRace.getId()));
        }
        request.setAttribute("horse", horse);
        request.setAttribute("listHorsesInRace", listHorsesInRace);
        request.setAttribute("races", races);
        return Pages.getPage(Pages.RACES_WITH_HORSE_PAGE);
    }
}
