package cz.cvut.kbss.ear.dao;

import cz.cvut.kbss.ear.MeetingRoomsApplication;
import cz.cvut.kbss.ear.environment.Generator;
import cz.cvut.kbss.ear.model.MeetingRoom;
import cz.cvut.kbss.ear.model.Reservation;
import cz.cvut.kbss.ear.service.SystemInitializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@DataJpaTest
@ComponentScan(basePackageClasses = MeetingRoomsApplication.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SystemInitializer.class),
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = TestConfiguration.class)})
public class ReservationDaoTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ReservationDao sut;

    @Test
    public void findAllReturnsAllReservations() {
        Reservation reservation;
        for(int i = 0; i < 5; i++) {
            reservation = Generator.generateReservation();
            em.persist(reservation);
        }

        final List<Reservation> result = sut.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, result.size());
    }

    @Test
    public void findAllReservationsForMeetingRoomReturnsReservationsForCorrectMeetingRoom() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoom();
        final Reservation reservation = Generator.generateReservationForMeetingRoom(meetingRoom);
        em.persist(reservation);

        final List<Reservation> result = sut.findAllReservationsForMeetingRoomById(meetingRoom.getId());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());

        final Reservation reservation2 = Generator.generateReservationForMeetingRoom(meetingRoom);
        em.persist(reservation2);

        final MeetingRoom meetingRoom3 = Generator.generateMeetingRoom();
        final Reservation reservation3 = Generator.generateReservationForMeetingRoom(meetingRoom3);
        em.persist(reservation3);

        final List<Reservation> result2 = sut.findAllReservationsForMeetingRoomById(meetingRoom.getId());

        Assertions.assertEquals(2, result2.size());
    }
}
