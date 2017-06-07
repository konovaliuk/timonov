package ua.timonov.web.project.exception;

/**
 * Exception that is thrown if error occurs in Service layer
 */
public class ServiceException extends AppException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
