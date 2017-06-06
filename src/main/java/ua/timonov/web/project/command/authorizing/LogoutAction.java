package ua.timonov.web.project.command.authorizing;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.util.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
//        request.getSession().removeAttribute("user");
        request.getSession().invalidate();
        return Pages.getPage(Pages.INDEX_PAGE);
    }
}
