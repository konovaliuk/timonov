package ua.timonov.web.project.command.race;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.race.RaceStatus;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SaveRacePlacesAction extends Action {

    public static final String RACE_EDIT = "raceEdit";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        long raceId = Long.valueOf(request.getParameter("raceId"));
        Race race = raceService.findById(raceId);
        List<HorseInRace> listOfHorsesInRace = horseInRaceService.findByRaceId(raceId);
        int nHorsesInRace = listOfHorsesInRace.size();

        List<Integer> places = new ArrayList<>();
        for (int i = 0; i < nHorsesInRace; i++) {
            places.add(Integer.valueOf(request.getParameter("places" + i)));
        }

        if (validateInputtedPlaces(new ArrayList<>(places))) {
            for (int i = 0; i < nHorsesInRace; i++) {
                HorseInRace horseInRace = listOfHorsesInRace.get(i);
                horseInRace.setFinishPlace(places.get(i));
                horseInRaceService.save(horseInRace);
            }
            raceService.save(race);
        } else {
            request.setAttribute("errorMessage", "Error while fixating results");
        }
        request.setAttribute("race", race);
        request.setAttribute("horsesInRace", listOfHorsesInRace);
        request.setAttribute("raceStatuses", RaceStatus.values());
        return CONFIG.getString(RACE_EDIT);
    }

    private boolean validateInputtedPlaces(List<Integer> places) {
        Collections.sort(places);
        for (int i = 0; i < places.size(); i++) {
            if (places.get(i) != i + 1) {
                return false;
            }
        }
        return true;
    }
}
