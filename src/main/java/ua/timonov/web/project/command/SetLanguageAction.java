package ua.timonov.web.project.command;

import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.util.ExceptionMessages;
import ua.timonov.web.project.util.Pages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * changes current locale to chosen language, redirects to main page
 */
public class SetLanguageAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        setLocale(request);
        return Pages.getPage(Pages.MAIN_PAGE);
    }

    protected void setLocale(HttpServletRequest request) {
        String lang = request.getParameter(Strings.LANGUAGE);
        if (lang.equals(Strings.EN)) {
            request.getSession().setAttribute(Strings.LANGUAGE, Strings.EN_US);
            ExceptionMessages.setLocale(ExceptionMessages.ENGLISH);
        } else {
            request.getSession().setAttribute(Strings.LANGUAGE, Strings.UK_UA);
            ExceptionMessages.setLocale(ExceptionMessages.UKRAINIAN);
        }
    }
}
