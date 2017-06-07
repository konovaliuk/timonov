package ua.timonov.web.project.dao;

import java.util.List;

/**
 * DAO interface for CRUD operations with entities
 * @param <Entity>      type of persisted entity
 */
public interface Dao<Entity> {

    /**
     * saves entity to database
     * @param entity    persisted entity
     * @return          true if operation succeed
     */
    boolean save(Entity entity);

    /**
     * deletes entity to database
     * @param id        ID of deleted entity
     * @return          true if operation succeed
     */
    boolean delete(Long id);

    /**
     * finds entity in database by its ID
     * @param id        entity ID
     * @return          found entity or null if not not found
     */
    Entity findById(Long id);

    /**
     * finds entity by another entity's ID that has foreign key on this one
     * @param id                        ID of other entity
     * @param foreignKeyEntityName      name of other entity
     * @return                          found entity
     */
    Entity findByForeignId(Long id, String foreignKeyEntityName);

    /**
     * finds all entities in database
     * @return          List of entities
     */
    List<Entity> findAll();

    /**
     * returns entity name
     * @return          entity name
     */
    String getName();
}
