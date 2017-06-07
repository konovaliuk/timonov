package ua.timonov.web.project.service;

import ua.timonov.web.project.dao.daointerface.CountryDao;
import ua.timonov.web.project.dao.daointerface.LocationDao;
import ua.timonov.web.project.model.location.Country;
import ua.timonov.web.project.model.location.Location;

/**
 * Represents service for interact with DAO layer interface CountryDao
 */
public class CountryService extends DataService<Country, Location> {

    private static CountryDao countryDao = daoFactory.createCountryDao();
    private static LocationDao locationDao = daoFactory.createLocationDao();
    private static final CountryService instance = new CountryService();

    private CountryService() {
        super(countryDao, locationDao);
    }

    public static CountryService getInstance() {
        return instance;
    }
}
