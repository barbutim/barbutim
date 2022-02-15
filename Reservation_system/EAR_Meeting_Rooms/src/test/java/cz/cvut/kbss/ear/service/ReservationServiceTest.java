package cz.cvut.kbss.ear.service;

import cz.cvut.kbss.ear.environment.Generator;
import cz.cvut.kbss.ear.model.MeetingRoom;
import cz.cvut.kbss.ear.model.Reservation;
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
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ReservationServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ReservationService sut;

    @MockBean
    private SystemInitializer initializerMock;

    @Test
    public void checkIfReservationDoesNotCollideWithOthersReturnsTrueIfReservationIsCorrectlyMade() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoom();
        em.persist(meetingRoom);
        List<Reservation> reservations = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            final Reservation reservation = Generator.generateReservationForMeetingRoom(meetingRoom);
            em.persist(reservation);
            reservations.add(reservation);
        }

        boolean result = sut.checkIfReservationDoesNotCollideWithOthers(reservations.get(0));

        Assert.assertTrue(result);
    }

    @Test
    public void persistWorksCorrectly() {
        final Reservation reservation = Generator.generateReservation();
        em.persist(reservation);

        final Reservation result = sut.findById(reservation.getId());
        Assert.assertEquals(reservation, result);
    }

    @Test
    public void updateWorksCorrectly() {
        final Reservation reservation = Generator.generateReservation();
        sut.persist(reservation);

        final Reservation newReservation = Generator.generateReservation();
        sut.update(newReservation);

        final Reservation result = em.find(Reservation.class, reservation.getId());
        Assert.assertEquals(reservation.getId(), result.getId());
    }

    @Test
    public void removeWorksCorrectly() {
        final Reservation reservation = Generator.generateReservation();
        em.persist(reservation);
        em.remove(reservation);

        final Reservation result = sut.findById(reservation.getId());
        Assert.assertNull(result);
    }

    @Test
    public void findByIdWorksCorrectly() {
        final Reservation reservation = Generator.generateReservation();
        em.persist(reservation);

        final Reservation result = sut.findById(reservation.getId());
        Assert.assertEquals(reservation, result);
    }

    @Test
    public void findAllWorksCorrectly() {
        for(int i = 0; i < 5; i++) {
            final Reservation reservation = Generator.generateReservation();
            em.persist(reservation);
        }

        final List<Reservation> result = sut.findAll();
        Assert.assertEquals(5, result.size());
    }
}
