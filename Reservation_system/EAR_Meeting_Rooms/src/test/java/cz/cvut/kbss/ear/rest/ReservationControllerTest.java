package cz.cvut.kbss.ear.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import cz.cvut.kbss.ear.environment.Generator;
import cz.cvut.kbss.ear.model.Reservation;
import cz.cvut.kbss.ear.service.ReservationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest extends BaseControllerTestRunner {

    @Mock
    private ReservationService reservationServiceMock;

    @InjectMocks
    private ReservationController sut;

    @BeforeEach
    public void setUp() {
        super.setUp(sut);
    }

    @Test
    public void getReservationsReturnsAllReservations() throws Exception {
        final List<Reservation> reservations = IntStream.range(0, 5).mapToObj(i -> Generator.generateReservation()).collect(
                Collectors.toList());
        Mockito.when(reservationServiceMock.findAll()).thenReturn(reservations);
        final MvcResult mvcResult = mockMvc.perform(get("/rest/reservation")).andReturn();
        final List<Reservation> result = readValue(mvcResult, new TypeReference<List<Reservation>>() {
        });
        Assertions.assertNotNull(result);
        Assertions.assertEquals(reservations.size(), result.size());
        for (int i = 0; i < reservations.size(); i++) {
            Assertions.assertEquals(reservations.get(i).getId(), result.get(i).getId());
        }
    }

    @Test
    public void getReservationByIdReturnsReservationWithId() throws Exception {
        final Reservation reservation = Generator.generateReservation();
        reservation.setId(123);
        when(reservationServiceMock.findById(reservation.getId())).thenReturn(reservation);
        final MvcResult mvcResult = mockMvc.perform(get("/rest/reservation/" + reservation.getId())).andReturn();
        final Reservation result = readValue(mvcResult, Reservation.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(reservation.getId(), result.getId());
        Assertions.assertEquals(reservation.getReservationCreatedOn(), result.getReservationCreatedOn());
    }

    @Test
    public void removeReservationRemovesReservation() throws Exception {
        final Reservation reservation = Generator.generateReservation();
        reservation.setId(123);

        when(reservationServiceMock.findById(reservation.getId())).thenReturn(reservation);
        mockMvc.perform(delete("/rest/reservation/" + reservation.getId())).andExpect(status().isNoContent());
        verify(reservationServiceMock).remove(reservation);
    }
}
