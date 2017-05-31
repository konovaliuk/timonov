package ua.timonov.web.project.command.race;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.race.RaceStatus;
import ua.timonov.web.project.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditRaceAction extends Action {

    public static final String RACE_EDIT = "raceEdit";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();
    private HorseService horseService = serviceFactory.createHorseService();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();
    private LocationService locationService = serviceFactory.createLocationService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        long raceId = Long.valueOf(request.getParameter("raceId"));
        Race race = raceService.findById(raceId);
        return prepareEditRacePage(request, race);
    }

    protected String prepareEditRacePage(HttpServletRequest request, Race race) {
        request.setAttribute("race", race);
        request.setAttribute("horsesInRace", horseInRaceService.findListByRaceId(race.getId()));
        request.setAttribute("raceStatuses", RaceStatus.values());
        request.setAttribute("raceStatusBeingFormed", RaceStatus.BEING_FORMED);
        request.setAttribute("raceStatusFinished", RaceStatus.FINISHED);
        request.setAttribute("raceStatusWinsPaid", RaceStatus.WINNINGS_PAID);
        request.setAttribute("locations", locationService.findAll());
        request.setAttribute("horses", horseService.findAll());
        return CONFIG.getString(RACE_EDIT);
    }
}

    /*@Deprecated
    private String choosePage(RaceStatus raceStatus) {
        return "/WEB-INF/jsp/race/edit" + raceStatus.nameForJspFile() + ".jsp";
    }*/