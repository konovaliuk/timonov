package ua.timonov.web.project.dao;

import java.util.List;

public interface IDao<Entity> {

    boolean save(Entity entity);

    boolean delete(Entity entity);

    Entity findById(long id);

    List<Entity> findAll();
}
