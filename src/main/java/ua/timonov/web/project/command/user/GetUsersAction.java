package ua.timonov.web.project.command.user;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.user.UserType;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.service.UserService;
import ua.timonov.web.project.util.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetUsersAction implements Action {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.createUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        return prepareUsersPage(request);
    }

    protected String prepareUsersPage(HttpServletRequest request) throws ServiceException {
        request.setAttribute("users", userService.findAll());
        request.setAttribute("userTypes", UserType.values());
        return Pages.getPage(Pages.USERS_PAGE);
    }
}
