package cz.cvut.kbss.ear.service;

import cz.cvut.kbss.ear.environment.Generator;
import cz.cvut.kbss.ear.model.Building;
import cz.cvut.kbss.ear.model.MeetingRoom;
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
public class BuildingServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private BuildingService sut;

    @MockBean
    private SystemInitializer initializerMock;

    @Test
    public void addMeetingRoomIntoBuildingAddsMeetingRoomIntoCorrectBuilding() {
        final Building building = Generator.generateBuilding();
        em.persist(building);
        final MeetingRoom meetingRoom = Generator.generateMeetingRoom();
        em.persist(meetingRoom);

        sut.addMeetingRoomIntoBuilding(building, meetingRoom);

        Assert.assertEquals(meetingRoom, building.getMeetingRooms().get(0));
    }

    @Test
    public void removeMeetingRoomFromBuildingRemovesMeetingRoomFromCorrectBuilding() {
        final Building building = Generator.generateBuilding();
        em.persist(building);
        final MeetingRoom meetingRoom = Generator.generateMeetingRoom();
        em.persist(meetingRoom);

        sut.addMeetingRoomIntoBuilding(building, meetingRoom);
        sut.removeMeetingRoomFromBuilding(building, meetingRoom);

        Assert.assertEquals(0, building.getMeetingRooms().size());
    }

    @Test
    public void persistWorksCorrectly() {
        final Building building = Generator.generateBuilding();
        em.persist(building);

        final Building result = sut.findById(building.getId());
        Assert.assertEquals(building, result);
    }

    @Test
    public void updateWorksCorrectly() {
        final Building building = Generator.generateBuilding();
        sut.persist(building);

        final Building newBuilding = Generator.generateBuilding();
        sut.update(newBuilding);

        final Building result = em.find(Building.class, building.getId());
        Assert.assertEquals(building.getId(), result.getId());
    }

    @Test
    public void removeWorksCorrectly() {
        final Building building = Generator.generateBuilding();
        em.persist(building);
        em.remove(building);

        final Building result = sut.findById(building.getId());
        Assert.assertNull(result);
    }

    @Test
    public void findByIdWorksCorrectly() {
        final Building building = Generator.generateBuilding();
        em.persist(building);

        final Building result = sut.findById(building.getId());
        Assert.assertEquals(building, result);
    }

    @Test
    public void findAllWorksCorrectly() {
        for(int i = 0; i < 5; i++) {
            final Building building = Generator.generateBuilding();
            em.persist(building);
        }

        final List<Building> result = sut.findAll();
        Assert.assertEquals(5, result.size());
    }
}
