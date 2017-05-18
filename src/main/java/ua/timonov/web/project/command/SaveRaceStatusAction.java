package ua.timonov.web.project.command;

import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.race.RaceStatus;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveRaceStatusAction extends Action {

    public static final String RACE_ADMIN_PAGE = "/WEB-INF/jsp/raceAdmin.jsp";

    private RaceService raceService = ServiceFactory.getInstance().getRaceService();
    private HorseInRaceService horseInRaceService = ServiceFactory.getInstance().getHorseInRaceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long raceId = Long.valueOf(request.getParameter("raceId"));
        Race race = raceService.getById(raceId);

        RaceStatus raceStatus = RaceStatus.valueOf(request.getParameter("raceStatus"));
        race.setRaceStatus(raceStatus);
        raceService.save(race);

        request.setAttribute("race", race);
        request.setAttribute("horsesInRace", horseInRaceService.getByRace(raceId));
        request.setAttribute("raceStatuses", RaceStatus.values());
        return RACE_ADMIN_PAGE;
    }
}
