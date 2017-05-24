package ua.timonov.web.project.command;

import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetRaceHorsesAction extends Action {

    public static final String RACE_PAGE = "/WEB-INF/jsp/race.jsp";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        long raceId = Long.valueOf(request.getParameter("raceId"));
        request.setAttribute("race", raceService.findById(raceId));
        request.setAttribute("horsesInRace", horseInRaceService.findByRaceId(raceId));
        return RACE_PAGE;
    }
}
