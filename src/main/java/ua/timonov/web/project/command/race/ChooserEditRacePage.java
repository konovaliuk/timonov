package ua.timonov.web.project.command.race;

import ua.timonov.web.project.model.race.RaceStatus;

@Deprecated
public final class ChooserEditRacePage {

    public static final String EDIT_NOT_ARRANGED_RACE = "/WEB-INF/jsp/race/editNotArranged.jsp";
    public static final String EDIT_OPEN_TO_BET_RACE = "/WEB-INF/jsp/race/editOpenToBet.jsp";
    public static final String EDIT_CLOSED_TO_BET_RACE = "/WEB-INF/jsp/race/editClosedToBet.jsp";
    public static final String EDIT_FINISHED_RACE = "/WEB-INF/jsp/race/editFinished.jsp";
    public static final String EDIT_RESULTS_FIXATED_RACE = "/WEB-INF/jsp/race/editResultFixated.jsp";
    public static final String SHOW_CLOSED_RACE = "/WEB-INF/jsp/race/showClosed.jsp";

    public static String choosePage(RaceStatus raceStatus) {
        switch (raceStatus) {
            case NOT_ARRANGED:
                return EDIT_NOT_ARRANGED_RACE;
            case OPEN_TO_BET:
                return EDIT_OPEN_TO_BET_RACE;
            case CLOSED_TO_BET:
                return EDIT_CLOSED_TO_BET_RACE;
            case FINISHED:
                return EDIT_FINISHED_RACE;
            case RESULTS_FIXATED:
                return EDIT_RESULTS_FIXATED_RACE;
            default:
                return SHOW_CLOSED_RACE;
        }
    }
}
