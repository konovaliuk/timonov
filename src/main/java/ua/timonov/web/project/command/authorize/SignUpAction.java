package ua.timonov.web.project.command.authorize;

import org.apache.log4j.Logger;
import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.user.Account;
import ua.timonov.web.project.model.user.Money;
import ua.timonov.web.project.model.user.User;
import ua.timonov.web.project.model.user.UserType;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.service.UserAccountService;
import ua.timonov.web.project.service.UserService;
import ua.timonov.web.project.util.LoggerMessages;
import ua.timonov.web.project.util.Pages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;


/**
 * performs signing up for new user
 */
public class SignUpAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(SignUpAction.class);

    ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.createUserService();
    private UserAccountService userAccountService = serviceFactory.createUserAccountService();
    private User user;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        user = createUserFromRequest(request);
        String passwordConfirm = request.getParameter(Strings.PASSWORD_CONFIRM);
        userService.validateUserInput(user, passwordConfirm);
        userAccountService.save(user.getAccount());
        userService.save(user);
        LOGGER.info(LoggerMessages.USER_SIGNED_UP + user.getLogin());
        request.setAttribute(Strings.MESSAGE_SUCCESS, true);
        return Pages.getPage(Pages.SIGN_UP_PAGE);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        request.setAttribute(Strings.MESSAGE_ERROR, e.getMessage());
        request.setAttribute(Strings.ERROR_DETAILS, e.getStackTrace());
        request.setAttribute(Strings.USER, user);
        LOGGER.warn(LoggerMessages.USER_NOT_SIGNED_UP + user.getLogin());
        return Pages.getPage(Pages.SIGN_UP_PAGE);
    }

    private User createUserFromRequest(HttpServletRequest request) {
        String login = request.getParameter(Strings.LOGIN);
        String password = request.getParameter(Strings.PASSWORD);
        String name = request.getParameter(Strings.NAME);
        Account account = new Account(new Money(BigDecimal.ZERO));
        return new User(UserType.CLIENT, login, password, name, account);
    }
}
