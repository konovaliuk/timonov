package ua.timonov.web.project.model.bet;

/**
 * Status of Bet
 */
public enum BetStatus {
    MADE, CANCELLED, PAID;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
