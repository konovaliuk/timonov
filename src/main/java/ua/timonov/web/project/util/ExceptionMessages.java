package ua.timonov.web.project.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Util class with property mapping for localization of exception messages
 */
public class ExceptionMessages {

    public final static String SAVE_FAILED = "saving.failed";
    public final static String DELETE_FAILED = "delete.failed";
    public final static String IMPOSSIBLE_TO_DELETE = "delete.impossible";
    public final static String ANOTHER_HOLDS = "delete.holds";
    public final static String FIND_ITEM_FAILED = "find.itemError";
    public final static String FIND_IN_TABLE = "find.inTable";
    public final static String FIND_ITEMS_FAILED = "find.itemsError";
    public final static String RACE_NOT_OPEN = "race.notOpen";
    public final static String HORSE_ALREADY_IN_RACE = "horse.alreadyInRace";
    public final static String HORSE_IN_RACE_ID = "horseInRace.withId";
    public final static String HORSE_IN_RACE_NOT_FOUND = "horseInRace.notFound";
    public final static String WON_RACES_MORE_TOTAL = "races.wonMoreTotal";
    public final static String ODDS_CHANCES_MORE_TOTAL = "odds.chancesMoreTotal";
    public final static String RACE_CANT_BE_DELETED = "race.cantBeDeleted";
    public final static String RACE_CANT_BE_CANCELLED = "race.cantBeCancelled";
    public final static String RACE_SHOULD_BE_TWO_HORSES = "race.shoudBeTwoHorses";
    public final static String HORSE_SHOULD_HAVE_ODDS = "horse.shouldHaveOneOdds";
    public final static String RACE_PLACES_ERROR = "race.placesError";
    public final static String NOT_ENOUGH_MONEY = "user.notEnoughMoney";
    public final static String SAME_LOGIN = "user.sameLogin";
    public final static String WRONG_LOGIN = "user.wrongLogin";
    public final static String WRONG_PASSWORD = "user.wrongPassword";
    public final static String LOGIN_NOT_MATCHES = "user.loginNotMatches";
    public final static String PASSWORD_NOT_MATCHES = "user.passwordNotMatches";
    public final static String DIFFERENT_PASSWORDS = "user.differentPasswords";
    public final static String ERROR_NOT_IDENTIFIED = "error.notIdentified";
    public final static String WRONG_VALUE = "parser.wrongValue";
    public final static String IN_FIELD = "parser.inField";
    public final static String ERROR_SETTING_PLACES = "race.errorSettingPlaces";
    public final static String BET_CANT_BE_CANCELLED = "bet.cantBeCancelled";

    public static final Locale ENGLISH = new Locale("en", "US");
    public static final Locale UKRAINIAN = new Locale("uk", "UA");

    private static final String BUNDLE_NAME = "/i18n/exceptions";
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, ENGLISH);

    public static void setLocale(Locale locale) {
        resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
    }

    public static String getMessage(String key) {
        return resourceBundle.getString(key);
    }
}
