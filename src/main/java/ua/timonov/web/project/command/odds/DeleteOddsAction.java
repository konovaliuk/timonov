package ua.timonov.web.project.command.odds;

import ua.timonov.web.project.command.horseinrace.GetHorseInRaceBookieAction;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.model.bet.Odds;
import ua.timonov.web.project.service.OddsService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteOddsAction extends GetHorseInRaceBookieAction {

    private OddsService oddsService = ServiceFactory.getInstance().createOddsService();
    private Odds odds;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Long oddsId = Long.valueOf(request.getParameter("oddsId"));
        odds = oddsService.findById(oddsId);

        oddsService.delete(oddsId);
        request.setAttribute("messageSuccess", true);

        return prepareHorseInRacePage(request, odds.getHorseInRaceId());
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        request.setAttribute("messageError", e.getMessage());
        request.setAttribute("errorDetails", e.getCause());
        return prepareHorseInRacePage(request, odds.getHorseInRaceId());
    }
}