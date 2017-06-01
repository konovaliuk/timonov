package ua.timonov.web.project.command;

import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.user.Account;
import ua.timonov.web.project.model.user.Money;
import ua.timonov.web.project.model.user.User;
import ua.timonov.web.project.model.user.UserType;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.service.UserAccountService;
import ua.timonov.web.project.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public class SignUpAction extends Action {

    public static final String INDEX_PAGE = "index";
    public static final String SIGN_UP_PAGE = "signUp";

    ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.createUserService();
    private UserAccountService userAccountService = serviceFactory.createUserAccountService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        User user = null;
        try {
            user = createUserFromRequest(request);
            String passwordConfirm = request.getParameter("passwordConfirm");

            userService.checkIdenticalPasswords(user, passwordConfirm);
            userService.findUserWithSameLogin(user);

            userAccountService.save(user.getAccount());
            userService.save(user);

            request.setAttribute("messageSuccess", true);
            return CONFIG.getString(SIGN_UP_PAGE);
        } catch (ServiceException e) {
            request.setAttribute("messageError", e.getMessage());
            request.setAttribute("errorDetails", e.getCause());
            request.setAttribute("user", user);
            return CONFIG.getString(SIGN_UP_PAGE);
        }
    }

    private User createUserFromRequest(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        Account account = new Account(new Money(BigDecimal.ZERO));
        return new User(UserType.CLIENT, login, password, name, account);
    }
}
