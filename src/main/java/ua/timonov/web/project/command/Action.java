package ua.timonov.web.project.command;

import ua.timonov.web.project.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {

    String execute(HttpServletRequest request, HttpServletResponse response) throws AppException;

    default String doOnError(HttpServletRequest request, Exception e) throws AppException {
        throw new RuntimeException(e);
    }

    default String handleError(HttpServletRequest request, Exception exception) {
        try {
            return doOnError(request, exception);
        } catch (AppException e) {
            throw new RuntimeException(exception);
        }
    }
}
