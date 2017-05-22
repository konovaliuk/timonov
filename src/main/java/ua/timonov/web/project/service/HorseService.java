package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.JdbcDataManager;
import ua.timonov.web.project.dao.jdbc.deprecated.HorseDao;
import ua.timonov.web.project.model.horse.Horse;

import java.util.List;

public class HorseService {
    private static final Logger LOGGER = Logger.getLogger(HorseService.class);
    private static final JdbcDataManager dataManager = JdbcDataManager.getInstance();

    private HorseDao horseDao = new HorseDao();

    public List<Horse> getAll() {
        return horseDao.getAll();
//        return horseDao.findAll().getResult();
    }
}
