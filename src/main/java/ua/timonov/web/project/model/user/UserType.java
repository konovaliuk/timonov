package ua.timonov.web.project.model.user;

public enum UserType {
    ADMIN, BOOKIE, CLIENT;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
