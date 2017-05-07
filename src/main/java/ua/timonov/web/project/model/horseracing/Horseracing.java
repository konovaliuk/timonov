package ua.timonov.web.project.model.horseracing;

/**
 *
 */
@Deprecated
public class HorseRacing {

    private static final HorseRacing instance = new HorseRacing();

    private HorseRacing() {
    }

    public static synchronized HorseRacing getInstance() {
        return instance;
    }
}
