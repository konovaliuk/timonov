package ua.timonov.web.project.command;

import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

abstract public class Action {

    public abstract String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException;
//    throws ServletException, IOException
}
