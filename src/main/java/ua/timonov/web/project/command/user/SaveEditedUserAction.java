package ua.timonov.web.project.command.user;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.user.Account;
import ua.timonov.web.project.model.user.Money;
import ua.timonov.web.project.model.user.User;
import ua.timonov.web.project.model.user.UserType;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.service.UserAccountService;
import ua.timonov.web.project.service.UserService;
import ua.timonov.web.project.util.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveEditedUserAction implements Action {

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

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        request.setAttribute("messageError", e.getMessage());
        request.setAttribute("errorDetails", e.getCause());
        return prepareUsersPage(request);
    }

    private void createClient(HttpServletRequest request, UserType userType) throws ServiceException {
        Account account = createAccountFromRequest(request);
        User user = createClientFromRequest(request, userType, account);
        // TODO
//            userService.findUserWithSameLogin(user);
        userAccountService.save(account);
        userService.save(user);
        request.setAttribute("messageSuccess", true);
        request.setAttribute("user", user);
    }

    private User createClientFromRequest(HttpServletRequest request, UserType userType, Account account) {
        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        return new User(userType, login, password, name, account);
    }

    private void createUser(HttpServletRequest request, UserType userType) throws ServiceException {
        User user = createUserFromRequest(request, userType);
        // TODO
//            userService.findUserWithSameLogin(user);
        userService.save(user);
        request.setAttribute("messageSuccess", true);
        request.setAttribute("user", user);
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
        return Pages.getPage(Pages.USER_EDIT_PAGE);
    }
}
