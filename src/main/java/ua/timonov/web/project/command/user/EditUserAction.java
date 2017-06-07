package ua.timonov.web.project.command.user;

import ua.timonov.web.project.command.Action;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.model.user.User;
import ua.timonov.web.project.model.user.UserType;
import ua.timonov.web.project.parser.Parser;
import ua.timonov.web.project.parser.ParserFactory;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.service.UserService;
import ua.timonov.web.project.util.Pages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * prepares page for edit user
 */
public class EditUserAction implements Action {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.createUserService();
    private Parser<Long> idParser = ParserFactory.createIdParser();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Long userId = idParser.parse(request.getParameter(Strings.USER_ID), Strings.USER_ID);
        User user = userService.findById(userId);
        return prepareEditUserPage(request, user);
    }

    private String prepareEditUserPage(HttpServletRequest request, User user) {
        request.setAttribute(Strings.USER, user);
        request.setAttribute(Strings.USER_TYPES, UserType.values());
        return Pages.getPage(Pages.USER_EDIT_PAGE);
    }
}
