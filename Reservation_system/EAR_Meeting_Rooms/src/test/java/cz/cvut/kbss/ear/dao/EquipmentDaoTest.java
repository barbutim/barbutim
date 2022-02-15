package cz.cvut.kbss.ear.dao;

import cz.cvut.kbss.ear.MeetingRoomsApplication;
import cz.cvut.kbss.ear.environment.Generator;
import cz.cvut.kbss.ear.environment.TestConfiguration;
import cz.cvut.kbss.ear.model.Equipment;
import cz.cvut.kbss.ear.service.SystemInitializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@DataJpaTest
@ComponentScan(basePackageClasses = MeetingRoomsApplication.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SystemInitializer.class),
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = TestConfiguration.class)})
public class EquipmentDaoTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private EquipmentDao sut;

    @Test
    public void findAllReturnsAllEquipments() {
        Equipment equipment;
        for(int i = 0; i < 5; i++) {
            equipment = Generator.generateEquipment();
            em.persist(equipment);
        }

        final List<Equipment> result = sut.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, result.size());
    }

    @Test
    public void findByNameReturnsEquipmentWithCorrectName() {
        final Equipment equipment = Generator.generateEquipment();
        em.persist(equipment);

        final Equipment result = sut.findByName(equipment.getName());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(equipment.getId(), result.getId());
    }

    @Test
    public void findByNameReturnsNullForUnknownName() {
        Assertions.assertNull(sut.findByName("UnknownName"));
    }
}