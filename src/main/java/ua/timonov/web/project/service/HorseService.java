package ua.timonov.web.project.service;

import ua.timonov.web.project.dao.Dao;
import ua.timonov.web.project.dao.daointerface.HorseDao;
import ua.timonov.web.project.model.horse.Horse;

public class HorseService extends DataService<Horse> {

    private static HorseDao horseDao = daoFactory.createHorseDao();
    private static final HorseService instance = new HorseService(horseDao);

    private HorseService(Dao<Horse> horseDao) {
        super(horseDao, "Horse");
    }

    public static HorseService getInstance() {
        return instance;
    }
}
