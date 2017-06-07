package ua.timonov.web.project.command.user;

import org.apache.log4j.Logger;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.exception.ServiceException;
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
 * deletes user
 */
public class DeleteUserAction extends GetUsersAction {

    private static final Logger LOGGER = Logger.getLogger(DeleteUserAction.class);

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.createUserService();
    private UserAccountService userAccountService = serviceFactory.createUserAccountService();
    private Parser<Long> idParser = ParserFactory.createIdParser();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Long userId = idParser.parse(request.getParameter(Strings.USER_ID), Strings.USER_ID);
        User user = userService.findById(userId);
        if (user.getUserType().equals(UserType.CLIENT)) {
            deleteClient(request, user);
        } else {
            deleteUser(request, user);
        }
        return prepareUsersPage(request);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        LOGGER.info(LoggerMessages.ERROR_DElETE_USER);
        request.setAttribute(Strings.MESSAGE_ERROR, e.getMessage());
        request.setAttribute(Strings.ERROR_DETAILS, e.getStackTrace());
        return prepareUsersPage(request);
    }

    private void deleteClient(HttpServletRequest request, User user) throws ServiceException {
        Long userAccountId = user.getAccount().getId();
        userService.delete(user.getId());
        userAccountService.delete(userAccountId);
        LOGGER.info(LoggerMessages.USER_DELETED);
        request.setAttribute(Strings.MESSAGE_SUCCESS, true);
    }

    private void deleteUser(HttpServletRequest request, User user) throws ServiceException {
        userService.delete(user.getId());
        LOGGER.info(LoggerMessages.USER_DELETED);
        request.setAttribute(Strings.MESSAGE_SUCCESS, true);
    }
}
