package ua.timonov.web.project.command;

import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.user.User;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignInAction extends Action {

    public static final String HOME_PAGE = "home";
    public static final String INDEX_PAGE = "index";

    private UserService userService = ServiceFactory.getInstance().createUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            User userFromRequest = createUserFromRequest(request);
            User user = userService.authorize(userFromRequest);
            request.getSession().setAttribute("user", user);
            request.getSession().setMaxInactiveInterval(10);
            request.setAttribute("messageSuccess", true);
            return CONFIG.getString(HOME_PAGE);
        } catch (ServiceException e) {
            request.setAttribute("messageError", e.getMessage());
            request.setAttribute("errorDetails", e.getCause());
            return CONFIG.getString(INDEX_PAGE);
        }
    }

    private User createUserFromRequest(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        return new User(login, password);
    }
}
