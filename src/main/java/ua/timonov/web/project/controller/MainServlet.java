package ua.timonov.web.project.controller;

import org.apache.log4j.Logger;
import ua.timonov.web.project.command.ActionInvoker;
import ua.timonov.web.project.parser.ParsingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(MainServlet.class);
    public static final String ERROR_PAGE = "/WEB-INF/jsp/error.jsp";

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
        } catch (ServletException | IOException | ParsingException e) {
            page = ERROR_PAGE;
            LOGGER.error(e.getMessage());
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
