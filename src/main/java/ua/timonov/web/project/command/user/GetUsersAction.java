package ua.timonov.web.project.command.user;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.user.UserType;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.service.UserService;
import ua.timonov.web.project.util.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetUsersAction extends Action {

    public static final String USERS_PAGE = "users";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.createUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        return prepareUsersPage(request);
    }

    protected String prepareUsersPage(HttpServletRequest request) {
        request.setAttribute("users", userService.findAll());
        request.setAttribute("userTypes", UserType.values());
        return Pages.getPage(Pages.USERS_PAGE);
    }
}
