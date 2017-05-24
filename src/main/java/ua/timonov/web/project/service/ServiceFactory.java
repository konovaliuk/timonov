package ua.timonov.web.project.service;

public class ServiceFactory {

    private static final ServiceFactory instance = new ServiceFactory();

    private static BetService betService = BetService.getInstance();
    private HorseInRaceService horseInRaceService = HorseInRaceService.getInstance();
    private HorseService horseService = HorseService.getInstance();
    private RaceService raceService = RaceService.getInstance();
    private OddsService oddsService = OddsService.getInstance();
    private UserService userService = UserService.getInstance();
    private LocationService locationService = LocationService.getInstance();
    private CountryService countryService = CountryService.getInstance();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public HorseService createHorseService() {
        return horseService;
    }

    public HorseInRaceService createHorseInRaceService() {
        return horseInRaceService;
    }

    public RaceService createRaceService() {
        return raceService;
    }

    public BetService createBetService() {
        return betService;
    }

    public OddsService createOddsService() {
        return oddsService;
    }

    public UserService createUserService() {
        return userService;
    }

    public LocationService createLocationService() {
        return locationService;
    }

    public CountryService createCountryService() {
        return countryService;
    }
}
