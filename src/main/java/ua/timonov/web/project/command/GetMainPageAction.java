package ua.timonov.web.project.command;

import ua.timonov.web.project.util.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetMainPageAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return Pages.getPage(Pages.MAIN_PAGE);
    }
}
