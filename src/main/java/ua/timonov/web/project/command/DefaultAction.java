package ua.timonov.web.project.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultAction extends Action {

    public static final String MAIN_PAGE = "/WEB-INF/jsp/index.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return MAIN_PAGE;
    }
}
