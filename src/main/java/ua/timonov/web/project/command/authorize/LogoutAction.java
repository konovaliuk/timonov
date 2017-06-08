package ua.timonov.web.project.command.authorize;

import org.apache.log4j.Logger;
import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.util.LoggerMessages;
import ua.timonov.web.project.util.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * performs user's logout, returns address to JSP page with login form
 */
public class LogoutAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(LogoutAction.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        LOGGER.info(LoggerMessages.USER_LOGGED_OUT);
        request.getSession().invalidate();
        return Pages.getPage(Pages.INDEX_PAGE);
    }
}
