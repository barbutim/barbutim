package cz.cvut.kbss.ear.rest;

import cz.cvut.kbss.ear.exception.NotFoundException;
import cz.cvut.kbss.ear.exception.ValidationException;
import cz.cvut.kbss.ear.model.Reservation;
import cz.cvut.kbss.ear.rest.handler.ErrorInfo;
import cz.cvut.kbss.ear.rest.handler.RestExceptionHandler;
import cz.cvut.kbss.ear.rest.util.RestUtils;
import cz.cvut.kbss.ear.service.MeetingRoomService;
import cz.cvut.kbss.ear.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/reservation")
public class ReservationController {

    private final static Logger LOG = LoggerFactory.getLogger(ReservationController.class);

    private final ReservationService reservationService;

    private final MeetingRoomService meetingRoomService;

    @Autowired
    public ReservationController(ReservationService reservationService, MeetingRoomService meetingRoomService) {
        this.reservationService = reservationService;
        this.meetingRoomService = meetingRoomService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @GetMapping(value = "/validation", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean getIfReservationIsAlright(@RequestBody Reservation reservation) {
        return reservationService.checkIfReservationDoesNotCollideWithOthers(reservation);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reservation> getReservations() {
        return reservationService.findAll();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Reservation getReservationById(@PathVariable int id) {
        final Reservation reservation = reservationService.findById(id);
        if(reservation == null) {
            throw NotFoundException.create("Reservation", id);
        }
        return reservation;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EMPLOYEE')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ErrorInfo> createReservation(@RequestBody Reservation reservation) {
        try {
            switch (reservation.getTime()) {
                case ALLDAY:
                    if (meetingRoomService.checkIfMeetingRoomIsAvailableDuringAllDayAtDate(reservation.getMeetingRoom(), reservation.getDateOfReservation())) {
                        reservationService.persist(reservation);
                    }
                case MORNING:
                    if (meetingRoomService.checkIfMeetingRoomIsAvailableInTheMorningAtDate(reservation.getMeetingRoom(), reservation.getDateOfReservation())) {
                        reservationService.persist(reservation);
                    }
                case AFTERNOON:
                    if (meetingRoomService.checkIfMeetingRoomIsAvailableInTheAfternoonAtDate(reservation.getMeetingRoom(), reservation.getDateOfReservation())) {
                        reservationService.persist(reservation);
                    }
            }
        }
        catch(Exception e) {
            LOG.warn("Reservation cannot be created!.", reservation.getId());
            return new RestExceptionHandler().handleException(e);
        }
        LOG.debug("Reservation with id {} has been created.", reservation.getId());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", reservation.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateReservation(@PathVariable int id, @RequestBody Reservation reservation) {
        final Reservation defaultReservation = reservationService.findById(id);
        if(!defaultReservation.getId().equals(reservation.getId())) {
            throw new ValidationException("ID does not match with the requested one.");
        }
        reservationService.update(reservation);
        LOG.debug("Reservation with id {} has been updated.", reservation.getId());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeReservation(@PathVariable int id) {
        final Reservation reservation = getReservationById(id);
        if(reservation == null) {
            return;
        }
        reservationService.remove(reservation);
        LOG.debug("Reservation with id {} has been deleted.", reservation.getId());
    }
}