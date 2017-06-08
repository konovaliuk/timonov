package ua.timonov.web.project.command.bet;

import org.apache.log4j.Logger;
import ua.timonov.web.project.command.user.GetUserBetsAction;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.service.BetService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.LoggerMessages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cancels current bet
 */
public class CancelBet extends GetUserBetsAction {

    private static final Logger LOGGER = Logger.getLogger(CancelBet.class);

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private BetService betService = serviceFactory.createBetService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Long betId = Long.valueOf(request.getParameter(Strings.BET_ID));
        betService.cancelBetById(betId);
        LOGGER.info(LoggerMessages.USER_CANCELLED_BET + betId);
        request.setAttribute(Strings.MESSAGE_SUCCESS, true);
        return prepareUserBetsPage(request);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        LOGGER.info(LoggerMessages.ERROR_CANCEL_BET);
        request.setAttribute(Strings.MESSAGE_ERROR, e.getMessage());
        request.setAttribute(Strings.ERROR_DETAILS, e.getStackTrace());
        return prepareUserBetsPage(request);
    }
}
