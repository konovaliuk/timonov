package ua.timonov.web.project.command.race;

import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class SaveRacePlacesAction extends EditRaceAction {

    public static final String RACE_EDIT = "raceEdit";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        long raceId = Long.valueOf(request.getParameter("raceId"));
        Race race = raceService.findById(raceId);
        List<HorseInRace> listOfHorsesInRace = horseInRaceService.findListByRaceId(raceId);
        List<Integer> inputtedPlaces = new ArrayList<>();
        for (int i = 0; i < listOfHorsesInRace.size(); i++) {
            inputtedPlaces.add(Integer.valueOf(request.getParameter("places" + i)));
        }
        try {
            raceService.saveInputtedPlaces(listOfHorsesInRace, inputtedPlaces);
            request.setAttribute("messageSuccess", true);
        } catch (ServiceException e) {
            request.setAttribute("messageError", e.getMessage());
            request.setAttribute("errorDetails", e.getCause());
            request.setAttribute("inputtedPlaces", inputtedPlaces);
        }
        return prepareEditRacePage(request, race);
    }


}
