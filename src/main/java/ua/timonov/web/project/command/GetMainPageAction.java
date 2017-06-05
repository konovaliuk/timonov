package ua.timonov.web.project.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetMainPageAction extends Action {

    public static final String MAIN_PAGE = "main";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return CONFIG.getString(MAIN_PAGE);
    }
}
