package cz.cvut.kbss.ear.service;

import cz.cvut.kbss.ear.dao.EquipmentDao;
import cz.cvut.kbss.ear.model.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class EquipmentService {

    private final EquipmentDao dao;

    @Autowired
    public EquipmentService(EquipmentDao dao) {
        this.dao = dao;
    }

    @Transactional
    public void persist(Equipment equipment) {
        Objects.requireNonNull(equipment);
        dao.persist(equipment);
    }

    @Transactional
    public void update(Equipment equipment) {
        Objects.requireNonNull(equipment);
        dao.update(equipment);
    }

    @Transactional
    public void remove(Equipment equipment) {
        Objects.requireNonNull(equipment);
        dao.remove(equipment);
    }

    @Transactional
    public Equipment findById(int id) {
        return dao.find(id);
    }

    @Transactional
    public List<Equipment> findAll() {
        return dao.findAll();
    }
}