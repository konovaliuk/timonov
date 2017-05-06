package ua.timonov.web.project.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorAction extends Action {

    public static final String ERROR_PAGE = "/WEB-INF/jsp/error.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return ERROR_PAGE;
    }
}
