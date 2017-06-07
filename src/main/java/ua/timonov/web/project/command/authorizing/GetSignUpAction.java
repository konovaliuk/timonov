package ua.timonov.web.project.command.authorizing;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.util.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * returns address to JSP page with sign up form
 */
public class GetSignUpAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        return Pages.getPage(Pages.SIGN_UP_PAGE);
    }
}
