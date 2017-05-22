package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.JdbcDataManager;
import ua.timonov.web.project.dao.daointerface.HorseDao;
import ua.timonov.web.project.dao.jdbc.mysql.MysqlDaoFactory;
import ua.timonov.web.project.model.horse.Horse;

import java.util.List;

public class HorseService {
    private static final Logger LOGGER = Logger.getLogger(HorseService.class);
    private static final JdbcDataManager dataManager = JdbcDataManager.getInstance();

    private HorseDao horseDao = MysqlDaoFactory.getInstance().createHorseDao();

    public List<Horse> findAll() {
        return horseDao.findAll();
//        return horseDao.findAll().getResult();
    }
}
