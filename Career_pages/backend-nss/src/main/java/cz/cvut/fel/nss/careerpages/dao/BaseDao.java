package cz.cvut.fel.nss.careerpages.dao;

import cz.cvut.fel.nss.careerpages.model.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @param <T>
 * Abstract class for basic daos
 */
public abstract class BaseDao<T extends AbstractEntity> implements GenericDao<T> {

    @PersistenceContext
    protected EntityManager em;

    protected final Class<T> type;

    protected BaseDao(Class<T> type) {
        this.type = type;
    }

    /**
     * @param id Identifier
     * @return finds by id
     * defined find method
     */
    @Override
    public T find(Long id) {
        Objects.requireNonNull(id);
        T object = em.find(type, id);
        if (object !=   null && object.isNotDeleted()) return object;
        return null;
    }

    /**
     * @return creates query
     * defined find all method
     */
    @Override
    public List<T> findAll() {
        try {
            return em.createQuery("SELECT e FROM " + type.getSimpleName() + " e WHERE e.deleted_at is null", type).getResultList();
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }


    /**
     * @param deleted
     * @return creates query
     * defined find all deleted method
     */
    public List<T> findAll(boolean deleted) {
        return em.createQuery("SELECT e FROM " + type.getSimpleName() + " e", type).getResultList();
    }

    /**
     * @param entity Entity to persist
     * standard persist method
     */
    @Override
    public void persist(T entity) {
        Objects.requireNonNull(entity);
        try {
            em.persist(entity);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     * @param entities Entities to persist
     * standard persist method for collections
     */
    @Override
    public void persist(Collection<T> entities) {
        Objects.requireNonNull(entities);
        if (entities.isEmpty()) {
            return;
        }
        try {
            entities.forEach(this::persist);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     * @param entity Entity to update
     * @return merge
     * update method
     */
    @Override
    public T update(T entity) {
        Objects.requireNonNull(entity);
        try {
            return em.merge(entity);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }


    /**
     * @param entity Entity to remove
     * remove method
     */
    @Override
    public void remove(T entity) {

        Objects.requireNonNull(entity);
        try {
            final T toRemove = em.merge(entity);
            if (toRemove != null) {
                em.remove(toRemove);
            }
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     * @param id Entity identifier
     * @return true false
     * if exists method
     */
    @Override
    public boolean exists(Long id) {
        return id != null && em.find(type, id) != null;
    }

}