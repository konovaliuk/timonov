package ua.timonov.web.project.model.bet;

/**
 * Bet type
 */
public enum BetType {
    WINNER, SECOND_PLACE, PRIZE_PLACE;

    public static final String SPACE = " ";
    public static final String UNDERSCORE = "_";

    @Override
    public String toString() {
        return name().toLowerCase().replace(UNDERSCORE, SPACE);
    }
}
