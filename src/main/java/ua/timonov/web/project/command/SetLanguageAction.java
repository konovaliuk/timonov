package ua.timonov.web.project.command;

import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.util.ExceptionMessages;
import ua.timonov.web.project.util.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetLanguageAction implements Action {

    public static final String LANG = "lang";
    public static final String EN = "en";
    public static final String EN_US = "en_US";
    public static final String UK_UA = "uk_UA";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        setLocale(request);
        return Pages.getPage(Pages.MAIN_PAGE);
    }

    protected void setLocale(HttpServletRequest request) {
        String lang = request.getParameter(LANG);
        if (lang.equals(EN)) {
            request.getSession().setAttribute(LANG, EN_US);
            ExceptionMessages.setLocale(ExceptionMessages.ENGLISH);
        } else {
            request.getSession().setAttribute(LANG, UK_UA);
            ExceptionMessages.setLocale(ExceptionMessages.UKRAINIAN);
        }
    }
}
