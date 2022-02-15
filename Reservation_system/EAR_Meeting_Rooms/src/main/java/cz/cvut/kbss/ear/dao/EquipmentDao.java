package cz.cvut.kbss.ear.dao;

import cz.cvut.kbss.ear.model.Equipment;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class EquipmentDao extends BaseDao<Equipment> {

    public EquipmentDao() {
        super(Equipment.class);
    }

    public List<Equipment> findAll() {
        return em.createNamedQuery("Equipment.findAll", Equipment.class).getResultList();
    }

    public Equipment findByName(String name) {
        try {
            return em.createNamedQuery("Equipment.findByName", Equipment.class).setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}