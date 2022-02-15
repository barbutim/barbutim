package cz.cvut.kbss.ear.dao;

import cz.cvut.kbss.ear.model.Building;
import cz.cvut.kbss.ear.model.MeetingRoom;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class BuildingDao extends BaseDao<Building> {

    public BuildingDao() {
        super(Building.class);
    }

    public List<Building> findAll() {
        return em.createNamedQuery("Building.findAll", Building.class).getResultList();
    }

    public Building findByName(String name) {
        try {
            return em.createNamedQuery("Building.findByName", Building.class).setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}