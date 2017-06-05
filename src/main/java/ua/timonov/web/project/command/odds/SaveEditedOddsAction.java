package ua.timonov.web.project.command.odds;

import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.model.bet.BetType;
import ua.timonov.web.project.model.bet.Odds;
import ua.timonov.web.project.service.OddsService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveEditedOddsAction extends EditOddsAction {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private OddsService oddsService = serviceFactory.createOddsService();
    private Odds odds;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        odds = createOddsFromRequest(request);

        oddsService.validateOddsRates(odds);
        oddsService.save(odds);
        request.setAttribute("messageSuccess", true);

        return prepareOddsEditPage(request, odds);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        request.setAttribute("messageError", e.getMessage());
        request.setAttribute("errorDetails", e.getCause());
        return prepareOddsEditPage(request, odds);
    }

    private Odds createOddsFromRequest(HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("oddsId"));
        Long horseInRaceId = Long.valueOf(request.getParameter("horseInRaceId"));
        BetType betType = BetType.valueOf(request.getParameter("betType"));
        int total = Integer.valueOf(request.getParameter("total"));
        int chances = Integer.valueOf(request.getParameter("chances"));
        return new Odds(id, horseInRaceId, betType, total, chances);
    }
}
