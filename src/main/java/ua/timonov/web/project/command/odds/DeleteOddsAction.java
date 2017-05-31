package ua.timonov.web.project.command.odds;

import ua.timonov.web.project.command.horseinrace.GetHorseInRaceBookieAction;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.bet.Odds;
import ua.timonov.web.project.service.OddsService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteOddsAction extends GetHorseInRaceBookieAction {

    private OddsService oddsService = ServiceFactory.getInstance().createOddsService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long oddsId = Long.valueOf(request.getParameter("oddsId"));
        Odds odds = oddsService.findById(oddsId);

        try {
            oddsService.delete(oddsId);
            request.setAttribute("messageSuccess", true);
        } catch (ServiceException e) {
            request.setAttribute("messageError", e.getMessage());
            request.setAttribute("errorDetails", e.getCause());
        }

        return prepareHorseInRacePage(request, odds.getHorseInRaceId());

//        request.setAttribute("horseInRace", horseInRaceService.findById(horseInRaceId));
//        request.setAttribute("race", raceService.findByHorseInRaceId(horseInRaceId));
//        request.setAttribute("betTypes", BetType.values());
//        return CONFIG.getString(HORSE_IN_RACE_BOOKIE);
    }
}