package ua.timonov.web.project.service;

public class ServiceFactory {

    private static final ServiceFactory instance = new ServiceFactory();

    private HorseInRaceService horseInRaceService = new HorseInRaceService();
    private RaceService raceService = new RaceService();
    private BetService betService = new BetService();
    private OddsService oddsService = new OddsService();
    private UserService userService = new UserService();
    private LocationService locationService = new LocationService();
    private CountryService countryService = new CountryService();

    public static ServiceFactory getInstance() {
        return instance;
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

    public LocationService getLocationService() {
        return locationService;
    }

    public CountryService getCountryService() {
        return countryService;
    }
}
