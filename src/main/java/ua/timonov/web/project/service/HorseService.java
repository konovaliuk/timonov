package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.DaoFactory;
import ua.timonov.web.project.dao.DatabaseType;
import ua.timonov.web.project.dao.daointerface.HorseDao;
import ua.timonov.web.project.model.horse.Horse;

import java.util.List;

public class HorseService {
    private static final Logger LOGGER = Logger.getLogger(HorseService.class);
    private static final HorseService instance = new HorseService();

    private DaoFactory daoFactory = DaoFactory.getFactory(DatabaseType.MYSQL);
    private HorseDao horseDao = daoFactory.createHorseDao();

    private HorseService() {
    }

    public static HorseService getInstance() {
        return instance;
    }

    public List<Horse> findAll() {
        return horseDao.findAll();
//        return horseDao.findAll().getResult();
    }
}
