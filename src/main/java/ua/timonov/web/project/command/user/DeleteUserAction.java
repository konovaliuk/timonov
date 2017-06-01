package ua.timonov.web.project.command.user;

import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.user.User;
import ua.timonov.web.project.model.user.UserType;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.service.UserAccountService;
import ua.timonov.web.project.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserAction extends GetUsersAction {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.createUserService();
    private UserAccountService userAccountService = serviceFactory.createUserAccountService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        long userId = Long.valueOf(request.getParameter("userId"));
        User user = userService.findById(userId);
        if (user.getUserType().equals(UserType.CLIENT)) {
            deleteClient(request, user);
        } else {
            deleteUser(request, user);
        }
        return prepareUsersPage(request);
    }

    private void deleteClient(HttpServletRequest request, User user) {
        try {
            long userAccountId = user.getAccount().getId();
            userService.delete(user.getId());
            userAccountService.delete(userAccountId);
            request.setAttribute("messageSuccess", true);
        } catch (ServiceException e) {
            request.setAttribute("messageError", e.getMessage());
            request.setAttribute("errorDetails", e.getCause());
        }
    }

    private void deleteUser(HttpServletRequest request, User user) {
        try {
            userService.delete(user.getId());
            request.setAttribute("messageSuccess", true);
        } catch (ServiceException e) {
            request.setAttribute("messageError", e.getMessage());
            request.setAttribute("errorDetails", e.getCause());
        }
    }
}
