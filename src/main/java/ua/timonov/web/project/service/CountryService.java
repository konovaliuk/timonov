package ua.timonov.web.project.service;

import ua.timonov.web.project.dao.jdbc.CountryDao;
import ua.timonov.web.project.model.location.Country;

import java.util.List;

public class CountryService {

    private CountryDao countryDao = new CountryDao();

    // TODO
    public List<Country> getAll() {
        return countryDao.getAll();
    }
}
