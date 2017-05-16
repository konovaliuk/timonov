package ua.timonov.web.project.service;

public class ServiceFactory {

    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private HorseInRaceService horseInRaceService = new HorseInRaceService();
    private RaceService raceService = new RaceService();
    private BetService betService = new BetService();
    private OddsService oddsService = new OddsService();
    private UserService userService = new UserService();

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    public HorseInRaceService getHorseInRaceService() {
        return horseInRaceService;
    }

    public RaceService getRaceService() {
        return raceService;
    }

    public BetService getBetService() {
        return betService;
    }

    public OddsService getOddsService() {
        return oddsService;
    }

    public UserService getUserService() {
        return userService;
    }
}
