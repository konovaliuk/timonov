package ua.timonov.web.project.command;

import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.race.RaceStatus;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class SaveRacePlacesAction extends Action {

    public static final String RACE_ADMIN_PAGE = "/WEB-INF/jsp/raceAdmin.jsp";

    private RaceService raceService = ServiceFactory.getInstance().getRaceService();
    private HorseInRaceService horseInRaceService = ServiceFactory.getInstance().getHorseInRaceService();


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long raceId = Long.valueOf(request.getParameter("raceId"));
        Race race = raceService.getById(raceId);
        List<HorseInRace> listOfHorsesInRace = horseInRaceService.getByRace(raceId);
        int nHorsesInRace = listOfHorsesInRace.size();

        List<Integer> places = new ArrayList<>();
        for (int i = 0; i < nHorsesInRace; i++) {
            places.add(Integer.valueOf(request.getParameter("places" + i)));
        }

        // TODO validate inputted places

        for (int i = 0; i < nHorsesInRace; i++) {
            HorseInRace horseInRace = listOfHorsesInRace.get(i);
            horseInRace.setFinishPlace(places.get(i));
            horseInRaceService.save(raceId, horseInRace);
        }
        raceService.save(race);

//        RaceStatus raceStatus = RaceStatus.valueOf(request.getParameter("raceStatus"));
//        race.setRaceStatus(raceStatus);

//        String[] places = request.getParameterValues("places");

//        int length2 = Integer.valueOf(request.getParameter("places.size"));
//        int length1 = Integer.valueOf(request.getParameter("places.length"));


        request.setAttribute("race", race);
        request.setAttribute("horsesInRace", listOfHorsesInRace);
        request.setAttribute("raceStatuses", RaceStatus.values());
        return RACE_ADMIN_PAGE;
    }
}
