package cz.cvut.kbss.ear.service;

import cz.cvut.kbss.ear.environment.Generator;
import cz.cvut.kbss.ear.model.Equipment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class EquipmentServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private EquipmentService sut;

    @MockBean
    private SystemInitializer initializerMock;

    @Test
    public void persistWorksCorrectly() {
        final Equipment equipment = Generator.generateEquipment();
        em.persist(equipment);

        final Equipment result = sut.findById(equipment.getId());
        Assert.assertEquals(equipment, result);
    }

    @Test
    public void updateWorksCorrectly() {
        final Equipment equipment = Generator.generateEquipment();
        sut.persist(equipment);

        final Equipment newEquipment = Generator.generateEquipment();
        sut.update(newEquipment);

        final Equipment result = em.find(Equipment.class, equipment.getId());
        Assert.assertEquals(equipment.getId(), result.getId());
    }

    @Test
    public void removeWorksCorrectly() {
        final Equipment equipment = Generator.generateEquipment();
        em.persist(equipment);
        em.remove(equipment);

        final Equipment result = sut.findById(equipment.getId());
        Assert.assertNull(result);
    }

    @Test
    public void findByIdWorksCorrectly() {
        final Equipment equipment = Generator.generateEquipment();
        em.persist(equipment);

        final Equipment result = sut.findById(equipment.getId());
        Assert.assertEquals(equipment, result);
    }

    @Test
    public void findAllWorksCorrectly() {
        for(int i = 0; i < 5; i++) {
            final Equipment equipment = Generator.generateEquipment();
            em.persist(equipment);
        }

        final List<Equipment> result = sut.findAll();
        Assert.assertEquals(5, result.size());
    }
}
