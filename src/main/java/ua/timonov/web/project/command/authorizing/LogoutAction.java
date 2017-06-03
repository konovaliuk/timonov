package ua.timonov.web.project.command.authorizing;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutAction extends Action {

    public static final String INDEX_PAGE = "index";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        request.getSession().setAttribute("logout", "true");
        request.getSession().removeAttribute("user");
        return CONFIG.getString(INDEX_PAGE);
    }
}
