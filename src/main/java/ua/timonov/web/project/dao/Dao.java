package ua.timonov.web.project.dao;

import java.util.List;

public interface Dao<Entity> {

    boolean save(Entity entity);

    boolean delete(long id);

    Entity findById(long id);

    Entity findByForeignId(long id, String foreignKeyEntityName);

    List<Entity> findAll();

    String getName();
}
