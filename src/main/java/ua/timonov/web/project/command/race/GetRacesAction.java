package ua.timonov.web.project.command.race;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetRacesAction extends Action {

    public static final String RACES = "races";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RaceService raceService = serviceFactory.createRaceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(RACES, raceService.findAll());
        return CONFIG.getString(RACES);
    }
}
