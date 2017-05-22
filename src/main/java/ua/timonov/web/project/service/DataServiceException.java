package ua.timonov.web.project.service;

public class DataServiceException extends Exception {
    public DataServiceException(String message) {
        super(message);
    }

    public DataServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
