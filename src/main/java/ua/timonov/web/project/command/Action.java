package ua.timonov.web.project.command;

import ua.timonov.web.project.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Represents action that will perform some logic
 */
public interface Action {

    /**
     * executes some action, returns address to JSP page which will be forwarded by servlet to show results
     * @param request           HTTP request from servlet
     * @param response          HTTP response from servlet
     * @return                  address to JSP page which will be forwarded by servlet
     * @throws AppException     if some error occurs while performing some logic
     */
    String execute(HttpServletRequest request, HttpServletResponse response) throws AppException;

    /**
     * @param request           HTTP request from servlet
     * @param e                 exception occurred in method execute
     * @return                  address to JSP page with message about error
     * @throws AppException     if some error occurs while preparing result page
     */
    default String doOnError(HttpServletRequest request, Exception e) throws AppException {
        throw new RuntimeException(e);
    }

    /**
     * handles an exception if it occurs in method execute
     * @param request           HTTP request from servlet
     * @param exception         exception occurred in method execute
     * @return                  address to JSP page with message about error
     * @throws                  RuntimeException if another error occurs
     */
    default String handleError(HttpServletRequest request, Exception exception) {
        try {
            return doOnError(request, exception);
        } catch (AppException e) {
            throw new RuntimeException(exception);
        }
    }
}
