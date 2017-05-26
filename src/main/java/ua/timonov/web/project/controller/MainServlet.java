package ua.timonov.web.project.controller;

import org.apache.log4j.Logger;
import ua.timonov.web.project.command.ActionInvoker;
import ua.timonov.web.project.exception.AppException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

public class MainServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(MainServlet.class);
    public static final ResourceBundle CONFIG = ResourceBundle.getBundle("config");
    public static final String ERROR_PAGE = "error";

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
            LOGGER.info("page: " + page);
        } catch (ServletException | IOException | AppException e) {
            LOGGER.error(e.getMessage());
            request.setAttribute("errorMessage", "Sorry! An error occurred");
            request.setAttribute("errorDetails", e.getMessage());
            page = CONFIG.getString(ERROR_PAGE);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
