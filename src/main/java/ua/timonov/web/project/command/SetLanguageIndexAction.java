package ua.timonov.web.project.command;

import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetLanguageIndexAction extends Action {

    public static final String INDEX_PAGE = "index";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        String lang = request.getParameter("lang");
        if (lang.equals("en")) {
            request.getSession().setAttribute("lang", "en_US");
        } else {
            request.getSession().setAttribute("lang", "uk_UA");
        }
        return CONFIG.getString(INDEX_PAGE);
    }
}
