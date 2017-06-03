package ua.timonov.web.project.model.race;

public enum RaceStatus {
    BEING_FORMED, OPEN_TO_BET, CLOSED_TO_BET, FINISHED, RESULTS_FIXATED, WINS_PAID, CANCELLED;

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

    @Deprecated
    public String nameForJspFile() {
        String result = "";
        for (String word : name().split(UNDERSCORE)) {
            result += word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
        }
        return result;
    }
}
