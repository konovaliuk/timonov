package ua.timonov.web.project.command.user;

import ua.timonov.web.project.exception.AppException;
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

public class AddUserAction extends GetUsersAction {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.createUserService();
    private UserAccountService userAccountService = serviceFactory.createUserAccountService();
    private User user;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        UserType userType = UserType.valueOf(request.getParameter("userType"));
        if (userType.equals(UserType.CLIENT)) {
            createClient(request, userType);
        } else {
            createUser(request, userType);
        }
        return prepareUsersPage(request);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        request.setAttribute("messageError", e.getMessage());
        request.setAttribute("errorDetails", e.getCause());
        request.setAttribute("userWithInputError", user);
        return prepareUsersPage(request);
    }

    private void createClient(HttpServletRequest request, UserType userType) throws ServiceException {
        Account account = createAccountFromRequest(request);
        user = createClientFromRequest(request, userType, account);
        userService.findUserWithSameLogin(user);
        userAccountService.save(account);
        userService.save(user);
        request.setAttribute("messageSuccess", true);
    }

    private User createClientFromRequest(HttpServletRequest request, UserType userType, Account account) {
        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        return new User(userType, login, password, name, account);
    }

    private void createUser(HttpServletRequest request, UserType userType) throws ServiceException {
        user = createUserFromRequest(request, userType);
        userService.findUserWithSameLogin(user);
        userService.save(user);
        request.setAttribute("messageSuccess", true);
    }

    private User createUserFromRequest(HttpServletRequest request, UserType userType) {
        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        return new User(userType, login, password, name);
    }

    private Account createAccountFromRequest(HttpServletRequest request) {
        double balance = Double.valueOf(request.getParameter("balance"));
        return new Account(new Money(balance));
    }
}
