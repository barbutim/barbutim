package cz.cvut.kbss.ear.rest;

import cz.cvut.kbss.ear.exception.NotFoundException;
import cz.cvut.kbss.ear.exception.ValidationException;
import cz.cvut.kbss.ear.model.Equipment;
import cz.cvut.kbss.ear.model.MeetingRoom;
import cz.cvut.kbss.ear.model.Reservation;
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

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rest/meetingroom")
public class MeetingRoomController {

    private final static Logger LOG = LoggerFactory.getLogger(MeetingRoomController.class);

    private final MeetingRoomService meetingRoomService;

    private final ReservationService reservationService;

    @Autowired
    public MeetingRoomController(MeetingRoomService meetingRoomService, ReservationService reservationService) {
        this.meetingRoomService = meetingRoomService;
        this.reservationService = reservationService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @PostMapping(value = "/equipment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addEquipmentIntoMeetingRoom(@RequestBody MeetingRoom meetingRoom, @RequestBody Equipment equipment) {
        meetingRoomService.addEquipmentIntoMeetingRoom(meetingRoom, equipment);
        LOG.debug("Equipment with id {} has been added into the meeting room.", equipment.getId());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @PostMapping(value = "/equipmentlist", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addListOfEquipmentsIntoMeetingRoom(@RequestBody MeetingRoom meetingRoom, @RequestBody List<Equipment> equipment) {
        meetingRoomService.addListOfEquipmentsIntoMeetingRoom(meetingRoom, equipment);
        LOG.debug("Equipments have been added into the meeting room.");
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @DeleteMapping("/equipment/{id}")
    public void removeEquipmentFromMeetingRoom(@RequestBody MeetingRoom meetingRoom, @RequestBody Equipment equipment) {
        meetingRoomService.removeEquipmentFromMeetingRoom(meetingRoom, equipment);
        LOG.debug("Equipment with id {} has been removed from the meeting room.", equipment.getId());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @DeleteMapping("/equipmentlist/{id}")
    public void removeListOfEquipmentsFromMeetingRoom(@RequestBody MeetingRoom meetingRoom, @RequestBody List<Equipment> equipment) {
        meetingRoomService.removeListOfEquipmentsFromMeetingRoom(meetingRoom, equipment);
        LOG.debug("Equipments have been removed from the meeting room.");
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EMPLOYEE')")
    @GetMapping(value = "/activity", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean getIfActive(@RequestBody MeetingRoom meetingRoom) {
        return meetingRoomService.checkIfMeetingRoomIsActive(meetingRoom);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @GetMapping(value = "/occupation", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean getIfFullyBooked(@RequestBody MeetingRoom meetingRoom, @RequestBody LocalDate date) {
        return meetingRoomService.checkIfMeetingRoomIsFullyBookedAtDate(meetingRoom, date);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EMPLOYEE')")
    @GetMapping(value = "/availability", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean getIfAvailable(@RequestBody MeetingRoom meetingRoom, @RequestBody LocalDate date) {
        return meetingRoomService.checkIfMeetingRoomIsAvailableAtDate(meetingRoom, date);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EMPLOYEE')")
    @GetMapping(value = "/availabilityMorning", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean getIfAvailableInTheMorning(@RequestBody MeetingRoom meetingRoom, @RequestBody LocalDate date) {
        return meetingRoomService.checkIfMeetingRoomIsAvailableInTheMorningAtDate(meetingRoom, date);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EMPLOYEE')")
    @GetMapping(value = "/availabilityAfternoon", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean getIfAvailableInTheAfternoon(@RequestBody MeetingRoom meetingRoom, @RequestBody LocalDate date) {
        return meetingRoomService.checkIfMeetingRoomIsAvailableInTheAfternoonAtDate(meetingRoom, date);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EMPLOYEE')")
    @GetMapping(value = "/availabilityAllDay", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean getIfAvailableDuringAllDay(@RequestBody MeetingRoom meetingRoom, @RequestBody LocalDate date) {
        return meetingRoomService.checkIfMeetingRoomIsAvailableDuringAllDayAtDate(meetingRoom, date);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EMPLOYEE')")
    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean getIfFulFillsCriteria(@RequestBody MeetingRoom meetingRoom, @RequestBody Integer maxPrice, Integer minCapacity) {
        return meetingRoomService.checkIfMeetingRoomFulFillsAllFilteredCriteria(meetingRoom, maxPrice, minCapacity);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MeetingRoom> getMeetingRooms() {
        return meetingRoomService.findAll();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MeetingRoom getMeetingRoomById(@PathVariable int id) {
        final MeetingRoom meetingRoom = meetingRoomService.findById(id);
        if(meetingRoom == null) {
            throw NotFoundException.create("Meeting room", id);
        }
        return meetingRoom;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createMeetingRoom(@RequestBody MeetingRoom meetingRoom) {
        meetingRoomService.persist(meetingRoom);
        LOG.debug("Meeting room with id {} has been created.", meetingRoom.getId());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", meetingRoom.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMeetingRoom(@PathVariable int id, @RequestBody MeetingRoom meetingRoom) {
        final MeetingRoom defaultMeetingRoom = meetingRoomService.findById(id);
        if(!defaultMeetingRoom.getId().equals(meetingRoom.getId())) {
            throw new ValidationException("ID does not match with the requested one.");
        }
        meetingRoomService.update(meetingRoom);
        LOG.debug("Meeting Room with id {} has been updated.", meetingRoom.getId());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeMeetingRoom(@PathVariable int id) {
        final MeetingRoom meetingRoom = getMeetingRoomById(id);
        if(meetingRoom == null) {
            return;
        }

        List<Reservation> reservations = reservationService.findAllReservationsForMeetingRoomById(meetingRoom.getId());
        for(Reservation reservation : reservations) {
            reservationService.remove(reservation);
        }

        meetingRoomService.remove(meetingRoom);
        LOG.debug("Meeting room with id {} has been deleted, together with its reservations.", meetingRoom.getId());
    }
}