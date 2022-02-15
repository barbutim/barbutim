package cz.cvut.kbss.ear.dao;

import cz.cvut.kbss.ear.MeetingRoomsApplication;
import cz.cvut.kbss.ear.environment.Generator;
import cz.cvut.kbss.ear.environment.ListGenerator;
import cz.cvut.kbss.ear.environment.TestConfiguration;
import cz.cvut.kbss.ear.model.Reservation;
import cz.cvut.kbss.ear.model.User;
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

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackageClasses = MeetingRoomsApplication.class, excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SystemInitializer.class),
    @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = TestConfiguration.class)})
public class UserDaoTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserDao sut;

    @Test
    public void findAllReturnsAllUsers() {
        User user;
        for(int i = 0; i < 5; i++) {
            user = Generator.generateUser();
            em.persist(user);
        }

        final List<User> result = sut.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, result.size());
    }

    @Test
    public void findByUsernameReturnsUserWithCorrectUsername() {
        final User user = Generator.generateUser();
        em.persist(user);

        final User result = sut.findByUsername(user.getUsername());

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
    }

    @Test
    public void findByUsernameReturnsNullForUnknownUsername() {
        assertNull(sut.findByUsername("UnknownUsername"));
    }

    @Test
    public void findAllUsersReservationsReturnsCorrectReservations() {
        List<Reservation> user1reservations, expectedReservations;

        final User user1 = Generator.generateUser();
        user1reservations = ListGenerator.generateListOfReservations();
        user1reservations.forEach(reservation -> reservation.setUser(user1));
        user1reservations.forEach(em::persist);
        em.persist(user1);
        expectedReservations = user1reservations;

        final User user2 = Generator.generateUser();
        List<Reservation> user2reservations = ListGenerator.generateListOfReservations();
        user2reservations.forEach(reservation -> reservation.setUser(user2));
        user2reservations.forEach(em::persist);
        em.persist(user2);

        List<Reservation> resultReservations = sut.findAllUsersReservations(user1.getId());

        assertNotNull(resultReservations);
        assertTrue(resultReservations.containsAll(expectedReservations));
    }
}
