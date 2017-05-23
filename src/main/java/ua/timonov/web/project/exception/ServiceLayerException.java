package ua.timonov.web.project.exception;

public class ServiceLayerException extends AppException {

    public ServiceLayerException(String message) {
        super(message);
    }

    public ServiceLayerException(String message, Throwable cause) {
        super(message, cause);
    }
}
