package ua.timonov.web.project.command.authorizing;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.user.User;
import ua.timonov.web.project.model.user.UserType;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.service.UserService;
import ua.timonov.web.project.util.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignInAction implements Action {

    private UserService userService = ServiceFactory.getInstance().createUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        User userFromRequest = createUserFromRequest(request);
        User user = userService.authorize(userFromRequest);

        request.getSession().setAttribute("loggedUser", user);
        request.getSession().setAttribute("roleAdmin", UserType.ADMIN);
        request.getSession().setAttribute("roleBookie", UserType.BOOKIE);
        request.getSession().setAttribute("roleClient", UserType.CLIENT);
        return Pages.getPage(Pages.MAIN_PAGE);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        request.setAttribute("messageError", e.getMessage());
        request.setAttribute("errorDetails", e.getCause());
        return Pages.getPage(Pages.INDEX_PAGE);

    }

    private User createUserFromRequest(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        return new User(login, password);
    }
}
