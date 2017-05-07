package ua.timonov.web.project.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.timonov.web.project.dao.JdbcDataManager;
import ua.timonov.web.project.model.horse.Horse;

import java.util.List;

public class HorseService {
    private static final Logger LOGGER = LogManager.getLogger(JdbcDataManager.class);
    private static final JdbcDataManager dataManager = JdbcDataManager.getInstance();

//    private DataSource dataSource = DataSourceFactory.getInstance().getDataSource();

    public List<Horse> getAll() {
        return dataManager.getAll().getResult();
    }

}
