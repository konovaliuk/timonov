package ua.timonov.web.project.service;

import ua.timonov.web.project.dao.DaoFactory;
import ua.timonov.web.project.dao.DatabaseType;
import ua.timonov.web.project.dao.daointerface.CountryDao;
import ua.timonov.web.project.model.location.Country;

import java.util.List;

public class CountryService {

    private static final CountryService instance = new CountryService();

    private DaoFactory daoFactory = DaoFactory.getFactory(DatabaseType.MYSQL);
    private CountryDao countryDao = daoFactory.createCountryDao();

    private CountryService() {
    }

    public static CountryService getInstance() {
        return instance;
    }

    // TODO
    public List<Country> getAll() {
        return countryDao.findAll();
    }
}
