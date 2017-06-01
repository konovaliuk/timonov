package ua.timonov.web.project.command.user;

import ua.timonov.web.project.command.Action;
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

public class SaveEditedUserAction extends Action {

    public static final String USER_EDIT = "userEdit";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.createUserService();
    private UserAccountService userAccountService = serviceFactory.createUserAccountService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        UserType userType = UserType.valueOf(request.getParameter("userType"));
        if (userType.equals(UserType.CLIENT)) {
            createClient(request, userType);
        } else {
            createUser(request, userType);
        }
        return prepareUsersPage(request);
    }

    private void createClient(HttpServletRequest request, UserType userType) {
        User user = null;
        try {
            Account account = createAccountFromRequest(request);
            user = createClientFromRequest(request, userType, account);
//            userService.findUserWithSameLogin(user);
            userAccountService.save(account);
            userService.save(user);
            request.setAttribute("messageSuccess", true);
            request.setAttribute("user", user);
        } catch (ServiceException e) {
            request.setAttribute("messageError", e.getMessage());
            request.setAttribute("errorDetails", e.getCause());
        }

    }

    private User createClientFromRequest(HttpServletRequest request, UserType userType, Account account) {
        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        return new User(userType, login, password, name, account);
    }

    private void createUser(HttpServletRequest request, UserType userType) {
        User user = null;
        try {
            user = createUserFromRequest(request, userType);
//            userService.findUserWithSameLogin(user);
            userService.save(user);
            request.setAttribute("messageSuccess", true);
            request.setAttribute("user", user);
        } catch (ServiceException e) {
            request.setAttribute("messageError", e.getMessage());
            request.setAttribute("errorDetails", e.getCause());
        }
    }

    private User createUserFromRequest(HttpServletRequest request, UserType userType) {
        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        return new User(userType, login, password, name);
    }

    private Account createAccountFromRequest(HttpServletRequest request) {
        long accountId = Long.valueOf(request.getParameter("accountId"));
        double balance = Double.valueOf(request.getParameter("balance"));
        return new Account(accountId, new Money(balance));
    }

    protected String prepareUsersPage(HttpServletRequest request) {
        request.setAttribute("userTypes", UserType.values());
        return CONFIG.getString(USER_EDIT);
    }
}
