package ua.timonov.web.project.model.bet;

public enum BetType {
    WINNER, SECOND_PLACE, PRIZE_PLACE, DOUBLE_EXPRESS, TRIPLE_EXPRESS, QUINELLA;

    public static final String SPACE = " ";
    public static final String UNDERSCORE = "_";

    @Override
    public String toString() {
        return name().toLowerCase().replace(UNDERSCORE, SPACE);
    }
}
