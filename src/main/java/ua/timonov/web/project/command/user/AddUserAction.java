package ua.timonov.web.project.command.user;

import org.apache.log4j.Logger;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.user.Account;
import ua.timonov.web.project.model.user.Money;
import ua.timonov.web.project.model.user.User;
import ua.timonov.web.project.model.user.UserType;
import ua.timonov.web.project.parser.Parser;
import ua.timonov.web.project.parser.ParserFactory;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.service.UserAccountService;
import ua.timonov.web.project.service.UserService;
import ua.timonov.web.project.util.LoggerMessages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * adds new user
 */
public class AddUserAction extends GetUsersAction {

    private static final Logger LOGGER = Logger.getLogger(AddUserAction.class);

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.createUserService();
    private UserAccountService userAccountService = serviceFactory.createUserAccountService();
    private Parser<Double> doubleParser = ParserFactory.createDoubleParser();

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
        LOGGER.info(LoggerMessages.ERROR_ADD_USER);
        request.setAttribute(Strings.MESSAGE_ERROR, e.getMessage());
        request.setAttribute(Strings.ERROR_DETAILS, e.getStackTrace());
        request.setAttribute("userWithInputError", user);
        return prepareUsersPage(request);
    }

    private void createClient(HttpServletRequest request, UserType userType) throws ServiceException, ParsingException {
        Account account = createAccountFromRequest(request);
        user = createClientFromRequest(request, userType, account);
        userService.findUserWithSameLogin(user);
        userAccountService.save(account);
        userService.save(user);
        LOGGER.info(LoggerMessages.USER_ADDED + user.getId());
        request.setAttribute(Strings.MESSAGE_SUCCESS, true);
    }

    private void createUser(HttpServletRequest request, UserType userType) throws ServiceException {
        user = createUserFromRequest(request, userType);
        userService.findUserWithSameLogin(user);
        userService.save(user);
        LOGGER.info(LoggerMessages.USER_ADDED + user.getId());
        request.setAttribute(Strings.MESSAGE_SUCCESS, true);
    }

    private User createClientFromRequest(HttpServletRequest request, UserType userType, Account account) {
        String name = request.getParameter(Strings.NAME);
        String login = request.getParameter(Strings.LOGIN);
        String password = request.getParameter(Strings.PASSWORD);
        return new User(userType, login, password, name, account);
    }

    private User createUserFromRequest(HttpServletRequest request, UserType userType) {
        String name = request.getParameter(Strings.NAME);
        String login = request.getParameter(Strings.LOGIN);
        String password = request.getParameter(Strings.PASSWORD);
        return new User(userType, login, password, name);
    }

    private Account createAccountFromRequest(HttpServletRequest request) throws ParsingException {
        double balance = doubleParser.parse(request.getParameter(Strings.BALANCE), Strings.BALANCE);
        return new Account(new Money(balance));
    }
}
