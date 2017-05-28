package ua.timonov.web.project.service;

import ua.timonov.web.project.dao.daointerface.HorseDao;
import ua.timonov.web.project.dao.daointerface.HorseInRaceDao;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.horse.Horse;
import ua.timonov.web.project.model.horse.HorseInRace;

public class HorseService extends DataService<Horse, HorseInRace> {

    private static HorseDao horseDao = daoFactory.createHorseDao();
    private static HorseInRaceDao horseInRaceDao = daoFactory.createHorseInRaceDao();
    private static final HorseService instance = new HorseService();

    private HorseService() {
        super(horseDao, horseInRaceDao);
    }

    public static HorseService getInstance() {
        return instance;
    }

    public Horse findByHorseIbRaceId(long horseInRaceId) {
        Horse horse = horseDao.findByForeignId(horseInRaceId, "HorseInRace");
//        Horse horse = horseDao.findByHorseInRaceId(horseInRaceId);
        if (horse == null) {
            throw new ServiceException("Horse in race " + horseInRaceId + " does not exist in table Horse");
        }
        return horse;
    }
}
