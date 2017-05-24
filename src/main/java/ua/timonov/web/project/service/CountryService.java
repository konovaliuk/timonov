package ua.timonov.web.project.service;

import ua.timonov.web.project.dao.Dao;
import ua.timonov.web.project.dao.daointerface.CountryDao;
import ua.timonov.web.project.model.location.Country;

public class CountryService extends DataService<Country> {

    private static CountryDao countryDao = daoFactory.createCountryDao();
    private static final CountryService instance = new CountryService(countryDao);

    private CountryService(Dao<Country> countryDao) {
        super(countryDao, "Country");
    }

    public static CountryService getInstance() {
        return instance;
    }
}
