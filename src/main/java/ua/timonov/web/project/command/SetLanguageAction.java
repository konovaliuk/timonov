package ua.timonov.web.project.command;

import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.util.ExceptionMessages;
import ua.timonov.web.project.util.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetLanguageAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        setLocale(request);
        return Pages.getPage(Pages.MAIN_PAGE);
    }

    protected void setLocale(HttpServletRequest request) {
        String lang = request.getParameter("lang");
        if (lang.equals("en")) {
            request.getSession().setAttribute("lang", "en_US");
            ExceptionMessages.setLocale(ExceptionMessages.ENGLISH);
        } else {
            request.getSession().setAttribute("lang", "uk_UA");
            ExceptionMessages.setLocale(ExceptionMessages.UKRAINIAN);
        }
    }
}
