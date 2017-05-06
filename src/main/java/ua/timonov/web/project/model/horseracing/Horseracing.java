package ua.timonov.web.project.model.horseracing;

/**
 *
 */
public class HorseRacing {

    private static HorseRacing instance;

    public static synchronized HorseRacing getInstance() {
        if (instance == null) {
            instance = new HorseRacing();
        }
        return instance;
    }
}
