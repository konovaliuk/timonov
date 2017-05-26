package ua.timonov.web.project.command;

import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;

abstract public class Action {

    public static final ResourceBundle CONFIG = ResourceBundle.getBundle("config");

    public abstract String execute(HttpServletRequest request, HttpServletResponse response)
            throws ParsingException, ServiceException;
//    throws ServletException, IOException
}
