package cz.cvut.kbss.ear.service;

import cz.cvut.kbss.ear.environment.DateTimeGenerator;
import cz.cvut.kbss.ear.environment.Generator;
import cz.cvut.kbss.ear.model.*;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MeetingRoomServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MeetingRoomService sut;

    @MockBean
    private SystemInitializer initializerMock;

    @Test
    public void addEquipmentIntoMeetingRoomAddsEquipmentIntoMeetingRoom() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoom();
        em.persist(meetingRoom);
        final Equipment equipment = Generator.generateEquipment();
        em.persist(equipment);

        sut.addEquipmentIntoMeetingRoom(meetingRoom, equipment);

        Assert.assertEquals(1, meetingRoom.getEquipmentList().size());
    }

    @Test
    public void addListOfEquipmentsIntoMeetingRoomAddsListOfEquipmentsIntoMeetingRoom() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoom();
        em.persist(meetingRoom);
        final List<Equipment> equipmentList = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            final Equipment equipment = Generator.generateEquipment();
            em.persist(equipment);
            equipmentList.add(equipment);
        }

        sut.addListOfEquipmentsIntoMeetingRoom(meetingRoom, equipmentList);

        Assert.assertEquals(5, meetingRoom.getEquipmentList().size());
    }

    @Test
    public void removeEquipmentFromMeetingRoomRemovesEquipmentFromMeetingRoom() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoom();
        em.persist(meetingRoom);
        final Equipment equipment = Generator.generateEquipment();
        em.persist(equipment);

        sut.addEquipmentIntoMeetingRoom(meetingRoom, equipment);
        sut.removeEquipmentFromMeetingRoom(meetingRoom, equipment);

        Assert.assertEquals(0, meetingRoom.getEquipmentList().size());
    }

    @Test
    public void removeListOfEquipmentsFromMeetingRoomRemovesListOfEquipmentsFromMeetingRoom() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoom();
        em.persist(meetingRoom);
        final List<Equipment> equipmentList = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            final Equipment equipment = Generator.generateEquipment();
            em.persist(equipment);
            equipmentList.add(equipment);
        }

        sut.addListOfEquipmentsIntoMeetingRoom(meetingRoom, equipmentList);
        sut.removeListOfEquipmentsFromMeetingRoom(meetingRoom, equipmentList);

        Assert.assertEquals(0, meetingRoom.getEquipmentList().size());
    }

    @Test
    public void checkIfMeetingRoomIsActiveReturnsTrueIfMeetingRoomIsActive() {
        final MeetingRoom activeMeetingRoom = Generator.generateMeetingRoomForActivityAndPrioritization(true, true);
        em.persist(activeMeetingRoom);
        final MeetingRoom inactiveMeetingRoom = Generator.generateMeetingRoomForActivityAndPrioritization(false, true);
        em.persist(inactiveMeetingRoom);

        final boolean result = sut.checkIfMeetingRoomIsActive(activeMeetingRoom);
        Assert.assertTrue(result);

        final boolean result2 = sut.checkIfMeetingRoomIsActive(inactiveMeetingRoom);
        Assert.assertFalse(result2);
    }

    @Test
    public void checkIfMeetingRoomIsFullyBookedAtDateReturnsTrueIfMeetingRoomIsFullyBookedAtDate() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoomForActivityAndPrioritization(true, true);
        em.persist(meetingRoom);
        final LocalDate date = DateTimeGenerator.generateDate();
        final Reservation reservation = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom, date, Time.ALLDAY, Status.PAID);
        em.persist(reservation);

        final LocalDate date2 = DateTimeGenerator.generateDate();
        final Reservation reservation2 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom, date2, Time.MORNING, Status.PAID);
        em.persist(reservation2);

        final boolean result = sut.checkIfMeetingRoomIsFullyBookedAtDate(meetingRoom, date);
        Assert.assertTrue(result);

        final boolean result2 = sut.checkIfMeetingRoomIsFullyBookedAtDate(meetingRoom, date2);
        Assert.assertFalse(result2);
    }

    /*@Test
    public void decideWhetherAddNotPrioritizedMeetingRoomsForSearchedRoomDecidesCorrectlyWhenToAddNotPrioritizedMeetingRoomsForSearchedRoom() {
        final List<MeetingRoom> allPrioritizedMeetingRooms = new ArrayList<>();
        final List<MeetingRoom> prioritizedMeetingRooms = new ArrayList<>();
        final List<MeetingRoom> notPrioritizedMeetingRooms = new ArrayList<>();

        final MeetingRoom meetingRoom = Generator.generateMeetingRoomForActivityAndPrioritization(true, true);
        em.persist(meetingRoom);
        allPrioritizedMeetingRooms.add(meetingRoom);
        prioritizedMeetingRooms.add(meetingRoom);

        for(int i = 0; i < 3; i++) {
            final MeetingRoom meetingRoom2 = Generator.generateMeetingRoomForActivityAndPrioritization(true, true);
            em.persist(meetingRoom2);
            allPrioritizedMeetingRooms.add(meetingRoom2);
        }

        for(int i = 0; i < 5; i++) {
            final MeetingRoom meetingRoom2 = Generator.generateMeetingRoomForActivityAndPrioritization(true, true);
            em.persist(meetingRoom2);
            notPrioritizedMeetingRooms.add(meetingRoom2);
        }

        final boolean result = sut.decideWhetherAddNotPrioritizedMeetingRoomsForSearchedRoom(notPrioritizedMeetingRooms.get(0), allPrioritizedMeetingRooms, prioritizedMeetingRooms, notPrioritizedMeetingRooms);
        Assert.assertTrue(result);
    }*/

    @Test
    public void checkIfMeetingRoomIsAvailableAtDateReturnsTrueIfMeetingRoomIsAvailableAtDate() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoomForActivityAndPrioritization(true, true);
        em.persist(meetingRoom);
        final LocalDate date = DateTimeGenerator.generateDate();
        final Reservation reservation = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom, date, Time.MORNING, Status.PAID);
        em.persist(reservation);

        final boolean result = sut.checkIfMeetingRoomIsAvailableAtDate(meetingRoom, date);
        Assert.assertTrue(result);
    }

    @Test
    public void checkIfMeetingRoomIsAvailableInTheMorningAtDateReturnsTrueIfMeetingRoomIsAvailableInTheMorningAtDate() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoomForActivityAndPrioritization(true, true);
        em.persist(meetingRoom);
        final LocalDate date = DateTimeGenerator.generateDate();
        final Reservation reservation = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom, date, Time.AFTERNOON, Status.PAID);
        em.persist(reservation);

        final boolean result = sut.checkIfMeetingRoomIsAvailableInTheMorningAtDate(meetingRoom, date);
        Assert.assertTrue(result);
    }

    @Test
    public void checkIfMeetingRoomIsAvailableInTheAfternoonAtDateReturnsTrueIfMeetingRoomIsAvailableInTheAfternoonAtDate() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoomForActivityAndPrioritization(true, true);
        em.persist(meetingRoom);
        final LocalDate date = DateTimeGenerator.generateDate();
        final Reservation reservation = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom, date, Time.AFTERNOON, Status.PAID);
        em.persist(reservation);

        final boolean result = sut.checkIfMeetingRoomIsAvailableInTheAfternoonAtDate(meetingRoom, date);
        Assert.assertFalse(result);
    }

    @Test
    public void checkIfMeetingRoomIsAvailableDuringAllDayAtDateReturnsTrueIfMeetingRoomIsAvailableDuringAllDayAtDate() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoomForActivityAndPrioritization(true, true);
        em.persist(meetingRoom);
        final LocalDate date = DateTimeGenerator.generateDate();
        final LocalDate date2 = DateTimeGenerator.generateDate();
        final Reservation reservation = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom, date, Time.AFTERNOON, Status.PAID);
        em.persist(reservation);

        final boolean result = sut.checkIfMeetingRoomIsAvailableDuringAllDayAtDate(meetingRoom, date2);
        Assert.assertTrue(result);
    }

    @Test
    public void checkIfMeetingRoomFulFillsAllFilteredCriteriaReturnsTrueIfMeetingRoomFulFillsAllFilteredCriteria() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoomWithAdjustedVariables(true, 50, 100, true);
        em.persist(meetingRoom);

        final boolean result = sut.checkIfMeetingRoomFulFillsAllFilteredCriteria(meetingRoom, 150, 50);
        Assert.assertTrue(result);

        final boolean result2 = sut.checkIfMeetingRoomFulFillsAllFilteredCriteria(meetingRoom, 150, 100);
        Assert.assertFalse(result2);
    }

    @Test
    public void persistWorksCorrectly() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoom();
        em.persist(meetingRoom);

        final MeetingRoom result = sut.findById(meetingRoom.getId());
        Assert.assertEquals(meetingRoom, result);
    }

    @Test
    public void updateWorksCorrectly() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoom();
        sut.persist(meetingRoom);

        final MeetingRoom newMeetingRoom = Generator.generateMeetingRoom();
        sut.update(newMeetingRoom);

        final MeetingRoom result = em.find(MeetingRoom.class, meetingRoom.getId());
        Assert.assertEquals(meetingRoom.getId(), result.getId());
    }

    @Test
    public void removeWorksCorrectly() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoom();
        em.persist(meetingRoom);
        em.remove(meetingRoom);

        final MeetingRoom result = sut.findById(meetingRoom.getId());
        Assert.assertNull(result);
    }

    @Test
    public void findByIdWorksCorrectly() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoom();
        em.persist(meetingRoom);

        final MeetingRoom result = sut.findById(meetingRoom.getId());
        Assert.assertEquals(meetingRoom, result);
    }

    @Test
    public void findAllWorksCorrectly() {
        for(int i = 0; i < 5; i++) {
            final MeetingRoom meetingRoom = Generator.generateMeetingRoom();
            em.persist(meetingRoom);
        }

        final List<MeetingRoom> result = sut.findAll();
        Assert.assertEquals(5, result.size());
    }
}
