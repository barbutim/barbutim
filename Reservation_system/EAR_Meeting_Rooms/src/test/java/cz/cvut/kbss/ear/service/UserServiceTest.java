package cz.cvut.kbss.ear.service;

import cz.cvut.kbss.ear.environment.Generator;
import cz.cvut.kbss.ear.model.Reservation;
import cz.cvut.kbss.ear.model.Status;
import cz.cvut.kbss.ear.model.User;
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
public class UserServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserService sut;

    @MockBean
    private SystemInitializer initializerMock;

    @Test
    public void checkIfUserHasAnyReservationsWithSpecifiedStatusReturnsTrueIfUserHasAReservationWithSpecifiedStatus() {
        final User user = Generator.generateUser();
        em.persist(user);
        final Reservation reservation = Generator.generateReservationForUserWithStatus(user, Status.CANCELED);
        em.persist(reservation);

        final boolean result = sut.checkIfUserHasAnyReservationsWithSpecifiedStatus(user, Status.CANCELED);
        Assert.assertTrue(result);

        final boolean result2 = sut.checkIfUserHasAnyReservationsWithSpecifiedStatus(user, Status.PAID);
        Assert.assertFalse(result2);
    }

    @Test
    public void getListOfUsersReservationsWithSpecifiedStatusReturnsListOfUsersReservationsWithSpecifiedStatus() {
        final User user = Generator.generateUser();
        em.persist(user);
        for(int i = 0; i < 5; i++) {
            final Reservation reservation = Generator.generateReservationForUserWithStatus(user, Status.CANCELED);
            em.persist(reservation);
        }

        final List<Reservation> result = sut.getListOfUsersReservationsWithSpecifiedStatus(user, Status.CANCELED);
        Assert.assertEquals(5, result.size());

        final List<Reservation> result2 = sut.getListOfUsersReservationsWithSpecifiedStatus(user, Status.PAID);
        Assert.assertEquals(0, result2.size());
    }

    @Test
    public void persistWorksCorrectly() {
        final User user = Generator.generateUser();
        em.persist(user);

        final User result = sut.findById(user.getId());
        Assert.assertEquals(user, result);
    }

    @Test
    public void updateWorksCorrectly() {
        final User user = Generator.generateUser();
        sut.persist(user);

        final User newUser = Generator.generateUser();
        sut.update(newUser);

        final User result = em.find(User.class, user.getId());
        Assert.assertEquals(user.getId(), result.getId());
    }

    @Test
    public void removeWorksCorrectly() {
        final User user = Generator.generateUser();
        em.persist(user);
        em.remove(user);

        final User result = sut.findById(user.getId());
        Assert.assertNull(result);
    }

    @Test
    public void findByIdWorksCorrectly() {
        final User user = Generator.generateUser();
        em.persist(user);

        final User result = sut.findById(user.getId());
        Assert.assertEquals(user, result);
    }

    @Test
    public void findAllWorksCorrectly() {
        for(int i = 0; i < 5; i++) {
            final User user = Generator.generateUser();
            em.persist(user);
        }

        // + 1
        final List<User> result = sut.findAll();
        Assert.assertEquals(6, result.size());
    }

    @Test
    public void existsWorksCorrectly() {
        final User user = Generator.generateUser();
        em.persist(user);

        final boolean result = sut.exists(user.getUsername());
        Assert.assertTrue(result);

        final boolean result2 = sut.exists("123TEST");
        Assert.assertFalse(result2);
    }
}
