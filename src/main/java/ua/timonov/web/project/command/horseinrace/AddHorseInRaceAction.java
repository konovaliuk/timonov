package ua.timonov.web.project.command.horseinrace;

import ua.timonov.web.project.command.race.ManageRaceAction;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.HorseService;
import ua.timonov.web.project.service.RaceService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddHorseInRaceAction extends ManageRaceAction {

    ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();
    private HorseService horseService = serviceFactory.createHorseService();
    private RaceService raceService = serviceFactory.createRaceService();
    private Race race;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        HorseInRace horseInRace = createHorseFromRequest(request);
        race = raceService.findById(horseInRace.getRaceId());

        horseInRaceService.save(horseInRace);
        request.setAttribute("messageSuccess", true);

        return prepareManageRacePage(request, race);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        request.setAttribute("messageError", e.getMessage());
        request.setAttribute("errorDetails", e.getCause());
        return prepareManageRacePage(request, race);
    }

    private HorseInRace createHorseFromRequest(HttpServletRequest request) throws ServiceException {
        Long raceId = Long.valueOf(request.getParameter("raceId"));
        Long horseId = Long.valueOf(request.getParameter("horseId"));
        Horse horse = horseService.findById(horseId);
        return new HorseInRace(raceId, horse);
    }
}
