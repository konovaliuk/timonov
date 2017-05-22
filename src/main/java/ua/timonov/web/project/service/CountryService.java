package ua.timonov.web.project.service;

import ua.timonov.web.project.dao.daointerface.CountryDao;
import ua.timonov.web.project.dao.jdbc.mysql.MysqlDaoFactory;
import ua.timonov.web.project.model.location.Country;

import java.util.List;

public class CountryService {

    private CountryDao countryDao = MysqlDaoFactory.getInstance().createCountryDao();

    // TODO
    public List<Country> getAll() {
        return countryDao.findAll();
    }
}
