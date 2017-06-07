package ua.timonov.web.project.model.user;

/**
 * Represents user type in application
 */
public enum UserType {
    ADMIN, BOOKIE, CLIENT;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
