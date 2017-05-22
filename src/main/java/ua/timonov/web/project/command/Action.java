package ua.timonov.web.project.command;

import ua.timonov.web.project.parser.ParsingException;
import ua.timonov.web.project.service.DataServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

abstract public class Action {

    public abstract String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, DataServiceException;
//    throws ServletException, IOException
}
