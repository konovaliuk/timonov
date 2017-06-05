package ua.timonov.web.project.util;

import java.util.ResourceBundle;

public class Pages {
    public final static String INDEX_PAGE = "index";
    public final static String MAIN_PAGE = "main";
    public final static String SIGN_UP_PAGE = "signUp";
    public final static String ERROR_PAGE = "error";
    public final static String BET_SAVED_PAGE = "betSaved";
    public final static String WON_BETS_PAGE = "wonBets";
    public final static String HORSES_PAGE = "horses";
    public final static String HORSE_EDIT_PAGE = "horseEdit";
    public final static String HORSE_IN_RACE_BOOKIE_PAGE = "horseInRaceBookie";
    public final static String HORSE_IN_RACE_PAGE = "horseInRace";
    public final static String RACES_PAGE = "races";
    public final static String RACE_PAGE = "race";
    public final static String RACE_MANAGE_PAGE = "raceManage";
    public final static String RACES_WITH_HORSE_PAGE = "racesWithHorse";
    public final static String USERS_PAGE = "users";
    public final static String USER_EDIT_PAGE = "userEdit";
    public final static String USER_BETS_PAGE = "userBets";
    public final static String ODDS_EDIT_PAGE = "oddsEdit";

    private static final String BUNDLE_NAME = "pages";

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);

    public static String getPage(String key) {
        return resourceBundle.getString(key);
    }

}
