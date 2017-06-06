package ua.timonov.web.project.command.user;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.model.user.User;
import ua.timonov.web.project.model.user.UserType;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.service.UserService;
import ua.timonov.web.project.util.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUserAction implements Action {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.createUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Long userId = Long.valueOf(request.getParameter("userId"));
        User user = userService.findById(userId);
        return prepareEditUserPage(request, user);
    }

    private String prepareEditUserPage(HttpServletRequest request, User user) {
        request.setAttribute("user", user);
        request.setAttribute("userTypes", UserType.values());
        return Pages.getPage(Pages.USER_EDIT_PAGE);
    }
}
