package ua.timonov.web.project.command;

import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.util.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * changes current locale to chosen language, redirects to start login page
 */
public class SetLanguageIndexAction extends SetLanguageAction {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        setLocale(request);
        return Pages.getPage(Pages.INDEX_PAGE);
    }
}
