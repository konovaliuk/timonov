package ua.timonov.web.project.dao.jdbc.mysql;

import ua.timonov.web.project.dao.daointerface.IHorseInRaceDao;
import ua.timonov.web.project.dao.jdbc.EntityDao;
import ua.timonov.web.project.model.horse.HorseInRace;

import java.util.List;

public class MysqlHorseInRaceDao extends EntityDao<HorseInRace> implements IHorseInRaceDao {

    private static final MysqlHorseInRaceDao instance = new MysqlHorseInRaceDao();

    private MysqlHorseInRaceDao() {
    }

    public static MysqlHorseInRaceDao getInstance() {
        return instance;
    }

    @Override
    public boolean save(HorseInRace horseInRace) {
        return false;
    }

    @Override
    public boolean delete(HorseInRace horseInRace) {
        return false;
    }

    @Override
    public HorseInRace findById(long id) {
        return null;
    }

    @Override
    public List<HorseInRace> findAll() {
        return null;
    }
}
