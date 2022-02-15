package cz.cvut.kbss.ear.dao;

import cz.cvut.kbss.ear.MeetingRoomsApplication;
import cz.cvut.kbss.ear.environment.Generator;
import cz.cvut.kbss.ear.environment.TestConfiguration;
import cz.cvut.kbss.ear.model.Building;
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
public class BuildingDaoTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private BuildingDao sut;

    @Test
    public void findAllReturnsAllBuildings() {
        Building building;
        for(int i = 0; i < 5; i++) {
            building = Generator.generateBuilding();
            em.persist(building);
        }

        final List<Building> result = sut.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, result.size());
    }

    @Test
    public void findByNameReturnsBuildingWithCorrectName() {
        final Building building = Generator.generateBuilding();
        em.persist(building);

        final Building result = sut.findByName(building.getName());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(building.getId(), result.getId());
    }

    @Test
    public void findByNameReturnsNullForUnknownName() {
        Assertions.assertNull(sut.findByName("UnknownName"));
    }

    @Test
    public void removeSetsIsRemovedToTrue() {
        final Building building = Generator.generateBuilding();
        sut.persist(building);

        final Building result = sut.findByName(building.getName());
        Assertions.assertFalse(result.isRemoved());
        sut.remove(building);

        final Building result2 = sut.findByName(building.getName());
        Assertions.assertTrue(result2.isRemoved());

        final List<Building> result3 = sut.findAll();
        Assertions.assertEquals(1, result3.size());
    }
}