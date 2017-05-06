package ua.timonov.web.project.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetRacesAction extends Action {

    public static final String RACES_PAGE = "/WEB-INF/jsp/races.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return RACES_PAGE;
    }
}
