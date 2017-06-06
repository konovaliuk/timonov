package ua.timonov.web.project.command.bet;

import ua.timonov.web.project.command.user.GetUserBetsAction;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.service.BetService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CancelBet extends GetUserBetsAction {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private BetService betService = serviceFactory.createBetService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Long betId = Long.valueOf(request.getParameter("betId"));
        betService.cancelBetById(betId);
        request.setAttribute("messageSuccess", true);
        return prepareUserBetsPage(request);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        request.setAttribute("messageError", e.getMessage());
        request.setAttribute("errorDetails", e.getCause());
        return prepareUserBetsPage(request);
    }
}
