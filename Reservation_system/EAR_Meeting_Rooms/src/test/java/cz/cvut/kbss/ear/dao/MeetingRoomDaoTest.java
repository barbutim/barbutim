package cz.cvut.kbss.ear.dao;

import cz.cvut.kbss.ear.MeetingRoomsApplication;
import cz.cvut.kbss.ear.environment.DateTimeGenerator;
import cz.cvut.kbss.ear.environment.Generator;
import cz.cvut.kbss.ear.environment.TestConfiguration;
import cz.cvut.kbss.ear.model.*;
import cz.cvut.kbss.ear.service.SystemInitializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@ComponentScan(basePackageClasses = MeetingRoomsApplication.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SystemInitializer.class),
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = TestConfiguration.class)})
public class MeetingRoomDaoTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MeetingRoomDao sut;

    @Test
    public void findAllActiveReturnsAllActiveMeetingRooms() {
        MeetingRoom meetingRoom;
        for(int i = 0; i < 3; i++) {
            meetingRoom = Generator.generateMeetingRoomForActivityAndPrioritization(true, true);
            em.persist(meetingRoom);
        }

        for(int i = 0; i < 2; i++) {
            meetingRoom = Generator.generateMeetingRoomForActivityAndPrioritization(false, true);
            em.persist(meetingRoom);
        }

        final List<MeetingRoom> result = sut.findAllActive();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void findAllReturnsAllMeetingRooms() {
        MeetingRoom meetingRoom;
        for(int i = 0; i < 5; i++) {
            meetingRoom = Generator.generateMeetingRoom();
            em.persist(meetingRoom);
        }

        final List<MeetingRoom> result = sut.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, result.size());
    }

    @Test
    public void findByNameReturnsMeetingRoomWithCorrectName() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoom();
        em.persist(meetingRoom);

        final MeetingRoom result = sut.findByName(meetingRoom.getName());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(meetingRoom.getId(), result.getId());
    }

    @Test
    public void findByNameReturnsNullForUnknownName() {
        Assertions.assertNull(sut.findByName("UnknownName"));
    }

    @Test
    public void findFullyBookedMeetingRoomsAtDateReturnsReservedMeetingRoomsForCorrectDate() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoom();
        final LocalDate date = DateTimeGenerator.generateDate();
        final Reservation reservation = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom, date, Time.ALLDAY, Status.PAID);
        em.persist(meetingRoom);
        em.persist(reservation);

        final List<MeetingRoom> result = sut.findFullyBookedMeetingRoomsAtDate(date);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());

        final MeetingRoom meetingRoom2 = Generator.generateMeetingRoom();
        final Reservation reservation2 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom2, date, Time.ALLDAY, Status.CANCELED);
        em.persist(meetingRoom2);
        em.persist(reservation2);

        final MeetingRoom meetingRoom3 = Generator.generateMeetingRoom();
        final LocalDate date3 = DateTimeGenerator.generateDate();
        final Reservation reservation3 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom3, date3, Time.ALLDAY, Status.ACTIVE);
        em.persist(meetingRoom3);
        em.persist(reservation3);

        final List<MeetingRoom> result2 = sut.findFullyBookedMeetingRoomsAtDate(date);

        Assertions.assertEquals(1, result2.size());
    }

    @Test
    public void findPrioritizedAvailableMeetingRoomsAtDateReturnsPrioritizedAvailableMeetingRoomsForCorrectDate() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoomForActivityAndPrioritization(true, false);
        final LocalDate date = DateTimeGenerator.generateDate();
        final Reservation reservation = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom, date, Time.ALLDAY, Status.PAID);
        em.persist(meetingRoom);
        em.persist(reservation);

        final List<MeetingRoom> result = sut.findPrioritizedAvailableMeetingRoomsAtDate(date);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());

        final MeetingRoom meetingRoom2 = Generator.generateMeetingRoomForActivityAndPrioritization(true, true);
        final Reservation reservation2 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom2, date, Time.MORNING, Status.ACTIVE);
        em.persist(meetingRoom2);
        em.persist(reservation2);

        final List<MeetingRoom> result2 = sut.findPrioritizedAvailableMeetingRoomsAtDate(date);
        Assertions.assertEquals(1, result2.size());

        final MeetingRoom meetingRoom3 = Generator.generateMeetingRoomForActivityAndPrioritization(true, true);
        final LocalDate date3 = DateTimeGenerator.generateDate();
        final Reservation reservation3 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom3, date3, Time.ALLDAY, Status.PAID);
        em.persist(meetingRoom3);
        em.persist(reservation3);

        final MeetingRoom meetingRoom4 = Generator.generateMeetingRoomForActivityAndPrioritization(false, true);
        final Reservation reservation4 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom4, date3, Time.ALLDAY, Status.CANCELED);
        em.persist(meetingRoom4);
        em.persist(reservation4);

        final List<MeetingRoom> result3 = sut.findPrioritizedAvailableMeetingRoomsAtDate(date);
        Assertions.assertEquals(2, result3.size());
    }

    @Test
    public void findNotPrioritizedAvailableMeetingRoomsAtDateReturnsNotPrioritizedAvailableMeetingRoomsForCorrectDate() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoomForActivityAndPrioritization(true, false);
        final LocalDate date = DateTimeGenerator.generateDate();
        final Reservation reservation = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom, date, Time.ALLDAY, Status.PAID);
        em.persist(meetingRoom);
        em.persist(reservation);

        final List<MeetingRoom> result = sut.findNotPrioritizedAvailableMeetingRoomsAtDate(date);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());

        final MeetingRoom meetingRoom2 = Generator.generateMeetingRoomForActivityAndPrioritization(true, false);
        final Reservation reservation2 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom2, date, Time.MORNING, Status.ACTIVE);
        em.persist(meetingRoom2);
        em.persist(reservation2);

        final List<MeetingRoom> result2 = sut.findNotPrioritizedAvailableMeetingRoomsAtDate(date);
        Assertions.assertEquals(1, result2.size());

        final MeetingRoom meetingRoom3 = Generator.generateMeetingRoomForActivityAndPrioritization(true, false);
        final LocalDate date3 = DateTimeGenerator.generateDate();
        final Reservation reservation3 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom3, date3, Time.ALLDAY, Status.REFUNDED);
        em.persist(meetingRoom3);
        em.persist(reservation3);

        final MeetingRoom meetingRoom4 = Generator.generateMeetingRoomForActivityAndPrioritization(false, true);
        final Reservation reservation4 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom4, date3, Time.ALLDAY, Status.CANCELED);
        em.persist(meetingRoom4);
        em.persist(reservation4);

        final List<MeetingRoom> result3 = sut.findNotPrioritizedAvailableMeetingRoomsAtDate(date);
        Assertions.assertEquals(2, result3.size());
    }

    @Test
    public void findPrioritizedAvailableMeetingRoomsInTheMorningAtDateReturnsPrioritizedAvailableMeetingRoomsInTheMorningForCorrectDate() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoomForActivityAndPrioritization(false, true);
        final LocalDate date = DateTimeGenerator.generateDate();
        final Reservation reservation = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom, date, Time.AFTERNOON, Status.ACTIVE);
        em.persist(meetingRoom);
        em.persist(reservation);

        final List<MeetingRoom> result = sut.findPrioritizedAvailableMeetingRoomsInTheMorningAtDate(date);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());

        final MeetingRoom meetingRoom2 = Generator.generateMeetingRoomForActivityAndPrioritization(true, true);
        final Reservation reservation2 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom2, date, Time.AFTERNOON, Status.PAID);
        em.persist(meetingRoom2);
        em.persist(reservation2);

        final List<MeetingRoom> result2 = sut.findPrioritizedAvailableMeetingRoomsInTheMorningAtDate(date);
        Assertions.assertEquals(1, result2.size());

        final MeetingRoom meetingRoom3 = Generator.generateMeetingRoomForActivityAndPrioritization(true, true);
        final LocalDate date3 = DateTimeGenerator.generateDate();
        final Reservation reservation3 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom3, date3, Time.MORNING, Status.CANCELED);
        em.persist(meetingRoom3);
        em.persist(reservation3);

        final MeetingRoom meetingRoom4 = Generator.generateMeetingRoomForActivityAndPrioritization(true, false);
        final Reservation reservation4 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom4, date3, Time.ALLDAY, Status.REFUNDED);
        em.persist(meetingRoom4);
        em.persist(reservation4);

        final List<MeetingRoom> result3 = sut.findPrioritizedAvailableMeetingRoomsInTheMorningAtDate(date);
        Assertions.assertEquals(2, result3.size());
    }

    @Test
    public void findNotPrioritizedAvailableMeetingRoomsInTheMorningAtDateReturnsNotPrioritizedAvailableMeetingRoomsInTheMorningForCorrectDate() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoomForActivityAndPrioritization(true, true);
        final LocalDate date = DateTimeGenerator.generateDate();
        final Reservation reservation = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom, date, Time.AFTERNOON, Status.ACTIVE);
        em.persist(meetingRoom);
        em.persist(reservation);

        final List<MeetingRoom> result = sut.findNotPrioritizedAvailableMeetingRoomsInTheMorningAtDate(date);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());

        final MeetingRoom meetingRoom2 = Generator.generateMeetingRoomForActivityAndPrioritization(true, false);
        final Reservation reservation2 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom2, date, Time.AFTERNOON, Status.PAID);
        em.persist(meetingRoom2);
        em.persist(reservation2);

        final List<MeetingRoom> result2 = sut.findNotPrioritizedAvailableMeetingRoomsInTheMorningAtDate(date);
        Assertions.assertEquals(1, result2.size());

        final MeetingRoom meetingRoom3 = Generator.generateMeetingRoomForActivityAndPrioritization(true, false);
        final LocalDate date3 = DateTimeGenerator.generateDate();
        final Reservation reservation3 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom3, date3, Time.ALLDAY, Status.REFUNDED);
        em.persist(meetingRoom3);
        em.persist(reservation3);

        final MeetingRoom meetingRoom4 = Generator.generateMeetingRoomForActivityAndPrioritization(false, true);
        final Reservation reservation4 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom4, date3, Time.MORNING, Status.ACTIVE);
        em.persist(meetingRoom4);
        em.persist(reservation4);

        final List<MeetingRoom> result3 = sut.findNotPrioritizedAvailableMeetingRoomsInTheMorningAtDate(date);
        Assertions.assertEquals(2, result3.size());
    }

    @Test
    public void findPrioritizedAvailableMeetingRoomsInTheAfternoonAtDateReturnsPrioritizedAvailableMeetingRoomsInTheAfternoonForCorrectDate() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoomForActivityAndPrioritization(false, true);
        final LocalDate date = DateTimeGenerator.generateDate();
        final Reservation reservation = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom, date, Time.MORNING, Status.ACTIVE);
        em.persist(meetingRoom);
        em.persist(reservation);

        final List<MeetingRoom> result = sut.findPrioritizedAvailableMeetingRoomsInTheAfternoonAtDate(date);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());

        final MeetingRoom meetingRoom2 = Generator.generateMeetingRoomForActivityAndPrioritization(true, true);
        final Reservation reservation2 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom2, date, Time.MORNING, Status.ACTIVE);
        em.persist(meetingRoom2);
        em.persist(reservation2);

        final List<MeetingRoom> result2 = sut.findPrioritizedAvailableMeetingRoomsInTheAfternoonAtDate(date);
        Assertions.assertEquals(1, result2.size());

        final MeetingRoom meetingRoom3 = Generator.generateMeetingRoomForActivityAndPrioritization(true, true);
        final LocalDate date3 = DateTimeGenerator.generateDate();
        final Reservation reservation3 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom3, date3, Time.MORNING, Status.PAID);
        em.persist(meetingRoom3);
        em.persist(reservation3);

        final MeetingRoom meetingRoom4 = Generator.generateMeetingRoomForActivityAndPrioritization(true, false);
        final Reservation reservation4 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom4, date3, Time.MORNING, Status.ACTIVE);
        em.persist(meetingRoom4);
        em.persist(reservation4);

        final List<MeetingRoom> result3 = sut.findPrioritizedAvailableMeetingRoomsInTheAfternoonAtDate(date);
        Assertions.assertEquals(2, result3.size());
    }

    @Test
    public void findNotPrioritizedAvailableMeetingRoomsInTheAfternoonAtDateReturnsNotPrioritizedAvailableMeetingRoomsInTheAfternoonForCorrectDate() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoomForActivityAndPrioritization(true, true);
        final LocalDate date = DateTimeGenerator.generateDate();
        final Reservation reservation = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom, date, Time.MORNING, Status.ACTIVE);
        em.persist(meetingRoom);
        em.persist(reservation);

        final List<MeetingRoom> result = sut.findNotPrioritizedAvailableMeetingRoomsInTheAfternoonAtDate(date);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());

        final MeetingRoom meetingRoom2 = Generator.generateMeetingRoomForActivityAndPrioritization(true, false);
        final Reservation reservation2 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom2, date, Time.MORNING, Status.PAID);
        em.persist(meetingRoom2);
        em.persist(reservation2);

        final List<MeetingRoom> result2 = sut.findNotPrioritizedAvailableMeetingRoomsInTheAfternoonAtDate(date);
        Assertions.assertEquals(1, result2.size());

        final MeetingRoom meetingRoom3 = Generator.generateMeetingRoomForActivityAndPrioritization(true, false);
        final LocalDate date3 = DateTimeGenerator.generateDate();
        final Reservation reservation3 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom3, date3, Time.AFTERNOON, Status.CANCELED);
        em.persist(meetingRoom3);
        em.persist(reservation3);

        final MeetingRoom meetingRoom4 = Generator.generateMeetingRoomForActivityAndPrioritization(false, false);
        final Reservation reservation4 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom4, date3, Time.MORNING, Status.ACTIVE);
        em.persist(meetingRoom4);
        em.persist(reservation4);

        final List<MeetingRoom> result3 = sut.findNotPrioritizedAvailableMeetingRoomsInTheAfternoonAtDate(date);
        Assertions.assertEquals(2, result3.size());
    }

    @Test
    public void findPrioritizedAvailableMeetingRoomsDuringAllDayAtDateReturnsPrioritizedAvailableMeetingRoomsDuringAllDayForCorrectDate() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoomForActivityAndPrioritization(true, true);
        final LocalDate date = DateTimeGenerator.generateDate();
        final Reservation reservation = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom, date, Time.MORNING, Status.PAID);
        em.persist(meetingRoom);
        em.persist(reservation);

        final List<MeetingRoom> result = sut.findPrioritizedAvailableMeetingRoomsDuringAllDayAtDate(date);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());

        final MeetingRoom meetingRoom2 = Generator.generateMeetingRoomForActivityAndPrioritization(true, true);
        final LocalDate date2 = DateTimeGenerator.generateDate();
        final Reservation reservation2 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom2, date2, Time.ALLDAY, Status.CANCELED);
        em.persist(meetingRoom2);
        em.persist(reservation2);

        final List<MeetingRoom> result2 = sut.findPrioritizedAvailableMeetingRoomsDuringAllDayAtDate(date);
        Assertions.assertEquals(1, result2.size());

        final MeetingRoom meetingRoom3 = Generator.generateMeetingRoomForActivityAndPrioritization(true, true);
        final LocalDate date3 = DateTimeGenerator.generateDate();
        final Reservation reservation3 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom3, date3, Time.MORNING, Status.REFUNDED);
        em.persist(meetingRoom3);
        em.persist(reservation3);

        final MeetingRoom meetingRoom4 = Generator.generateMeetingRoomForActivityAndPrioritization(false, true);
        final Reservation reservation4 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom4, date3, Time.MORNING, Status.ACTIVE);
        em.persist(meetingRoom4);
        em.persist(reservation4);

        final List<MeetingRoom> result3 = sut.findPrioritizedAvailableMeetingRoomsDuringAllDayAtDate(date);
        Assertions.assertEquals(2, result3.size());
    }

    @Test
    public void findNotPrioritizedAvailableMeetingRoomsDuringAllDayAtDateReturnsNotPrioritizedAvailableMeetingRoomsDuringAllDayForCorrectDate() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoomForActivityAndPrioritization(true, false);
        final LocalDate date = DateTimeGenerator.generateDate();
        final Reservation reservation = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom, date, Time.ALLDAY, Status.ACTIVE);
        em.persist(meetingRoom);
        em.persist(reservation);

        final List<MeetingRoom> result = sut.findNotPrioritizedAvailableMeetingRoomsDuringAllDayAtDate(date);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());

        final MeetingRoom meetingRoom2 = Generator.generateMeetingRoomForActivityAndPrioritization(true, false);
        final LocalDate date2 = DateTimeGenerator.generateDate();
        final Reservation reservation2 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom2, date2, Time.AFTERNOON, Status.REFUNDED);
        em.persist(meetingRoom2);
        em.persist(reservation2);

        final List<MeetingRoom> result2 = sut.findNotPrioritizedAvailableMeetingRoomsDuringAllDayAtDate(date);
        Assertions.assertEquals(1, result2.size());

        final MeetingRoom meetingRoom3 = Generator.generateMeetingRoomForActivityAndPrioritization(true, false);
        final LocalDate date3 = DateTimeGenerator.generateDate();
        final Reservation reservation3 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom3, date3, Time.MORNING, Status.REFUNDED);
        em.persist(meetingRoom3);
        em.persist(reservation3);

        final MeetingRoom meetingRoom4 = Generator.generateMeetingRoomForActivityAndPrioritization(true, true);
        final Reservation reservation4 = Generator.generateReservationForMeetingRoomAtDateAndTimeWithStatus(meetingRoom4, date3, Time.ALLDAY, Status.PAID);
        em.persist(meetingRoom4);
        em.persist(reservation4);

        final List<MeetingRoom> result3 = sut.findNotPrioritizedAvailableMeetingRoomsDuringAllDayAtDate(date);
        Assertions.assertEquals(2, result3.size());
    }

    @Test
    public void findPrioritizedAvailableFilteredMeetingRoomsReturnsCorrectlyPrioritizedFilteredMeetingRooms() {
        final boolean prioritized = true;
        final int maxPricePerHalfDay = 100;
        final int minCapacity = 50;
        final boolean active = true;
        final MeetingRoom meetingRoom = Generator.generateMeetingRoomWithAdjustedVariables(prioritized, maxPricePerHalfDay, minCapacity, active);
        em.persist(meetingRoom);

        final List<MeetingRoom> result = sut.findPrioritizedFilteredMeetingRooms(maxPricePerHalfDay, minCapacity);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());

        final List<MeetingRoom> result2 = sut.findPrioritizedFilteredMeetingRooms(maxPricePerHalfDay-1, minCapacity);
        Assertions.assertEquals(1, result2.size());

        final boolean prioritized2 = false;
        final int minCapacity3 = 40;
        final MeetingRoom meetingRoom2 = Generator.generateMeetingRoomWithAdjustedVariables(prioritized2, maxPricePerHalfDay, minCapacity, active);
        em.persist(meetingRoom2);

        final MeetingRoom meetingRoom3 = Generator.generateMeetingRoomWithAdjustedVariables(prioritized, maxPricePerHalfDay, minCapacity3, active);
        em.persist(meetingRoom3);

        final List<MeetingRoom> result3 = sut.findPrioritizedFilteredMeetingRooms(maxPricePerHalfDay, minCapacity);
        Assertions.assertEquals(2, result3.size());
    }

    @Test
    public void findNotPrioritizedAvailableFilteredMeetingRoomsReturnsCorrectlyNotPrioritizedFilteredMeetingRooms() {
        final boolean prioritized = false;
        final int maxPricePerHalfDay = 100;
        final int minCapacity = 50;
        final boolean active = true;
        final MeetingRoom meetingRoom = Generator.generateMeetingRoomWithAdjustedVariables(prioritized, maxPricePerHalfDay, minCapacity, active);
        em.persist(meetingRoom);

        final List<MeetingRoom> result = sut.findNotPrioritizedFilteredMeetingRooms(maxPricePerHalfDay, minCapacity);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());

        final List<MeetingRoom> result2 = sut.findNotPrioritizedFilteredMeetingRooms(maxPricePerHalfDay-1, minCapacity);
        Assertions.assertEquals(1, result2.size());

        final boolean active2 = false;
        final int minCapacity3 = 40;
        final MeetingRoom meetingRoom2 = Generator.generateMeetingRoomWithAdjustedVariables(prioritized, maxPricePerHalfDay, minCapacity, active2);
        em.persist(meetingRoom2);

        final MeetingRoom meetingRoom3 = Generator.generateMeetingRoomWithAdjustedVariables(prioritized, maxPricePerHalfDay, minCapacity3, active);
        em.persist(meetingRoom3);

        final List<MeetingRoom> result3 = sut.findNotPrioritizedFilteredMeetingRooms(maxPricePerHalfDay, minCapacity);
        Assertions.assertEquals(2, result3.size());
    }

    /*@Test
    public void findAllPrioritizedReturnsAllPrioritizedMeetingRooms() {
        MeetingRoom meetingRoom;
        for(int i = 0; i < 3; i++) {
            meetingRoom = Generator.generateMeetingRoomForActivityAndPrioritization(true, true);
            em.persist(meetingRoom);
        }

        for(int i = 0; i < 2; i++) {
            meetingRoom = Generator.generateMeetingRoomForActivityAndPrioritization(true, false);
            em.persist(meetingRoom);
        }

        final int result = sut.findNumberOfPrioritized();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result);
    }*/

    @Test
    public void findAllNotPrioritizedReturnsAllNotPrioritizedMeetingRooms() {
        MeetingRoom meetingRoom;
        for(int i = 0; i < 3; i++) {
            meetingRoom = Generator.generateMeetingRoomForActivityAndPrioritization(true, false);
            em.persist(meetingRoom);
        }

        for(int i = 0; i < 2; i++) {
            meetingRoom = Generator.generateMeetingRoomForActivityAndPrioritization(false, true);
            em.persist(meetingRoom);
        }

        final int result = sut.findNumberOfNotPrioritized();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result);
    }
}
