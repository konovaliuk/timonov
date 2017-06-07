package ua.timonov.web.project.controller;

import org.apache.log4j.Logger;
import ua.timonov.web.project.command.ActionInvoker;
import ua.timonov.web.project.util.ExceptionMessages;
import ua.timonov.web.project.util.Pages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * HTTP servlet for application
 */
public class MainServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(MainServlet.class);

    private static final ActionInvoker actionInvoker = ActionInvoker.getInstance();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response) throws ServletException, IOException {
        String page;
        try {
            page = actionInvoker.invoke(request, response);
            LOGGER.info(Strings.OPEN_PAGE + page);
        } catch (RuntimeException e) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.ERROR_NOT_IDENTIFIED);
            LOGGER.error(message);
            LOGGER.error(e.getMessage());
            request.setAttribute(Strings.MESSAGE_ERROR, e.getMessage());
            request.setAttribute(Strings.ERROR_DETAILS, e.getStackTrace());
            page = Pages.getPage(Pages.ERROR_PAGE);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
