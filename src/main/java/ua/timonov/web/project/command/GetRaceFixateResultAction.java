package ua.timonov.web.project.command;

import ua.timonov.web.project.model.race.RaceStatus;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetRaceFixateResultAction extends Action {

    public static final String RACE_ADMIN_PAGE = "/WEB-INF/jsp/raceAdmin.jsp";

    private RaceService raceService = ServiceFactory.getInstance().getRaceService();
    private HorseInRaceService horseInRaceService = ServiceFactory.getInstance().getHorseInRaceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long raceId = Long.valueOf(request.getParameter("raceId"));
        request.setAttribute("race", raceService.getById(raceId));
        request.setAttribute("horsesInRace", horseInRaceService.getByRace(raceId));
        request.setAttribute("raceStatuses", RaceStatus.values());
        return RACE_ADMIN_PAGE;
    }
}
