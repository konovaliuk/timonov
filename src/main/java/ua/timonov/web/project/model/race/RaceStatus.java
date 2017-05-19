package ua.timonov.web.project.model.race;

public enum RaceStatus {
    NOT_ARRANGED, OPEN_TO_BET, CLOSED_TO_BET, IN_PROCESS, RESULTS_FIXATED;

    public static final String SPACE = " ";
    public static final String UNDERSCORE = "_";

    @Override
    public String toString() {
        return name().toLowerCase().replace(UNDERSCORE, SPACE);
    }
}
