package ua.timonov.web.project.command.authorize;

import org.apache.log4j.Logger;
import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.user.User;
import ua.timonov.web.project.model.user.UserType;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.service.UserService;
import ua.timonov.web.project.util.LoggerMessages;
import ua.timonov.web.project.util.Pages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * checks user credentials and lets him to sign in
 */
public class SignInAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(SignInAction.class);

    private UserService userService = ServiceFactory.getInstance().createUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        User userFromRequest = createUserFromRequest(request);
        User user = userService.authorize(userFromRequest);
        LOGGER.info(LoggerMessages.USER_SIGN_IN + user.getLogin());
        request.getSession().setAttribute(Strings.LOGGED_USER, user);
        request.getSession().setAttribute(Strings.ROLE_ADMIN, UserType.ADMIN);
        request.getSession().setAttribute(Strings.ROLE_BOOKIE, UserType.BOOKIE);
        request.getSession().setAttribute(Strings.ROLE_CLIENT, UserType.CLIENT);
        return Pages.getPage(Pages.MAIN_PAGE);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        LOGGER.warn(LoggerMessages.USER_NOT_SIGN_IN);
        request.setAttribute(Strings.MESSAGE_ERROR, e.getMessage());
        request.setAttribute(Strings.ERROR_DETAILS, e.getStackTrace());
        return Pages.getPage(Pages.INDEX_PAGE);

    }

    private User createUserFromRequest(HttpServletRequest request) {
        String login = request.getParameter(Strings.LOGIN);
        String password = request.getParameter(Strings.PASSWORD);
        return new User(login, password);
    }
}
