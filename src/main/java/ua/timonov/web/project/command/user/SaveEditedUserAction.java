package ua.timonov.web.project.command.user;

import org.apache.log4j.Logger;
import ua.timonov.web.project.command.Action;
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
import ua.timonov.web.project.util.Pages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * saves edited user
 */
public class SaveEditedUserAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(DeleteUserAction.class);

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.createUserService();
    private UserAccountService userAccountService = serviceFactory.createUserAccountService();
    private Parser<Long> idParser = ParserFactory.createIdParser();
    private User user;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        UserType userType = UserType.valueOf(request.getParameter(Strings.USER_TYPE));
        if (userType.equals(UserType.CLIENT)) {
            createClient(request, userType);
        } else {
            createUser(request, userType);
        }
        return prepareUsersPage(request);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        LOGGER.info(LoggerMessages.ERROR_EDITING_USER);
        request.setAttribute(Strings.MESSAGE_ERROR, e.getMessage());
        request.setAttribute(Strings.ERROR_DETAILS, e.getStackTrace());
        return prepareUsersPage(request);
    }

    private void createClient(HttpServletRequest request, UserType userType) throws ServiceException, ParsingException {
        Account account = createAccountFromRequest(request);
        user = createClientFromRequest(request, userType, account);
        userService.findUserWithSameLoginAndAnotherId(user);
        userAccountService.save(account);
        userService.save(user);
        LOGGER.info(LoggerMessages.USER_SAVE_EDITED);
        request.setAttribute(Strings.MESSAGE_SUCCESS, true);
        request.setAttribute(Strings.USER, user);
    }

    private User createClientFromRequest(HttpServletRequest request, UserType userType, Account account) throws ParsingException {
        Long userId = idParser.parse(request.getParameter(Strings.USER_ID), Strings.USER_ID);
        String name = request.getParameter(Strings.NAME);
        String login = request.getParameter(Strings.LOGIN);
        String password = request.getParameter(Strings.PASSWORD);
        return new User(userId, userType, login, password, name, account);
    }

    private void createUser(HttpServletRequest request, UserType userType) throws ServiceException {
        user = createUserFromRequest(request, userType);
        userService.findUserWithSameLoginAndAnotherId(user);
        userService.save(user);
        LOGGER.info(LoggerMessages.USER_SAVE_EDITED);
        request.setAttribute(Strings.MESSAGE_SUCCESS, true);
        request.setAttribute(Strings.USER, user);
    }

    private User createUserFromRequest(HttpServletRequest request, UserType userType) {
        Long userId = Long.valueOf(request.getParameter(Strings.USER_ID));
        String name = request.getParameter(Strings.NAME);
        String login = request.getParameter(Strings.LOGIN);
        String password = request.getParameter(Strings.PASSWORD);
        return new User(userId, userType, login, password, name);
    }

    private Account createAccountFromRequest(HttpServletRequest request) throws ParsingException {
        Long accountId = idParser.parse(request.getParameter(Strings.ACCOUNT_ID), Strings.ACCOUNT_ID);
        double balance = Double.valueOf(request.getParameter(Strings.BALANCE));
        return new Account(accountId, new Money(balance));
    }

    protected String prepareUsersPage(HttpServletRequest request) {
        request.setAttribute(Strings.USER, user);
        request.setAttribute(Strings.USER_TYPES, UserType.values());
        return Pages.getPage(Pages.USER_EDIT_PAGE);
    }
}
