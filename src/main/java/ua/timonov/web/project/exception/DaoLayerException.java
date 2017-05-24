package ua.timonov.web.project.exception;

@Deprecated
public class DaoLayerException extends AppException {

    public DaoLayerException(String message) {
        super(message);
    }

    public DaoLayerException(String message, Throwable cause) {
        super(message, cause);
    }
}
