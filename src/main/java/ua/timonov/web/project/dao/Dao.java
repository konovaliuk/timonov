package ua.timonov.web.project.dao;

import java.util.List;

public interface Dao<Entity> {

    boolean save(Entity entity, long... externalId);

    boolean delete(long id);

    Entity findById(long id);

    List<Entity> findAll();

    String getName();
}
