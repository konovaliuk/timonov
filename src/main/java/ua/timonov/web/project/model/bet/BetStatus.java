package ua.timonov.web.project.model.bet;

public enum BetStatus {
    MADE, CANCELLED, PAID;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
