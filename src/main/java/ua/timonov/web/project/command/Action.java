package ua.timonov.web.project.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

abstract public class Action {

    public abstract String execute(HttpServletRequest request, HttpServletResponse response);
//    throws ServletException, IOException
}
