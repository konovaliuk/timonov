package ua.timonov.web.project.model.race;

/**
 * Race status
 */
public enum RaceStatus {
    /* race is being formed by admin, bookie can set bet rates (odds) */
    BEING_FORMED,
    /* clients can make bets */
    OPEN_TO_BET,
    /* bets are not allowed */
    CLOSED_TO_BET,
    /* race is finished */
    FINISHED,
    /* horse places are set by admin */
    RESULTS_FIXATED,
    /* clients take their wins */
    WINS_PAID,
    /* race is cancelled, bets are returned */
    CANCELLED;

    public static final String SPACE = " ";
    public static final String UNDERSCORE = "_";

    @Override
    public String toString() {
        return name().toLowerCase().replace(UNDERSCORE, SPACE);
    }

    public RaceStatus nextPossibleStatus() {
        if (this.ordinal() < WINS_PAID.ordinal()) {
            return values()[ordinal() + 1];
        }
        return this;
    }
}
