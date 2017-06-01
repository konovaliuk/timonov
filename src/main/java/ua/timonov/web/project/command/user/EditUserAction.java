package ua.timonov.web.project.command.user;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.user.User;
import ua.timonov.web.project.model.user.UserType;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUserAction extends Action {

    public static final String USER_EDIT = "userEdit";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.createUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        long userId = Long.valueOf(request.getParameter("userId"));
        User user = userService.findById(userId);
        return prepareEditUserPage(request, user);
    }

    private String prepareEditUserPage(HttpServletRequest request, User user) {
        request.setAttribute("user", user);
        request.setAttribute("userTypes", UserType.values());
        return CONFIG.getString(USER_EDIT);
    }
}
