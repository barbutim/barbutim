package cz.cvut.kbss.ear.rest;

import cz.cvut.kbss.ear.exception.NotFoundException;
import cz.cvut.kbss.ear.exception.ValidationException;
import cz.cvut.kbss.ear.model.Building;
import cz.cvut.kbss.ear.model.MeetingRoom;
import cz.cvut.kbss.ear.model.Reservation;
import cz.cvut.kbss.ear.rest.util.RestUtils;
import cz.cvut.kbss.ear.service.BuildingService;
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
@RequestMapping("/rest/building")
public class BuildingController {

    private final static Logger LOG = LoggerFactory.getLogger(BuildingController.class);

    private final BuildingService buildingService;

    private final MeetingRoomService meetingRoomService;

    private final ReservationService reservationService;

    @Autowired
    public BuildingController(BuildingService buildingService, MeetingRoomService meetingRoomService, ReservationService reservationService) {
        this.buildingService = buildingService;
        this.meetingRoomService = meetingRoomService;
        this.reservationService = reservationService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/meetingroom", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addMeetingRoomIntoBuilding(@RequestBody Building building, @RequestBody MeetingRoom meetingRoom) {
        List<Building> buildings = buildingService.findAll();
        boolean idCheck = false;
        for(int i = 0; i < buildings.size(); i++) {
            if(buildings.get(i).getId().equals(building.getId())) {
                idCheck = true;
                i = buildings.size();
            }
        }
        if(!idCheck) {
            throw new ValidationException("Wrong building.");
        }
        else {
            buildingService.addMeetingRoomIntoBuilding(building, meetingRoom);
            LOG.debug("Meeting room with id {} has been added into the building.", meetingRoom.getId());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/meetingroom")
    public void removeMeetingRoomFromBuilding(@RequestBody Building building, @RequestBody MeetingRoom meetingRoom) {
        buildingService.removeMeetingRoomFromBuilding(building, meetingRoom);
        LOG.debug("Meeting room with id {} has been removed from the building.", meetingRoom.getId());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Building> getBuildings() {
        return buildingService.findAll();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Building getBuildingById(@PathVariable int id) {
        final Building building = buildingService.findById(id);
        if(building == null) {
            throw NotFoundException.create("Building", id);
        }
        return building;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createBuilding(@RequestBody Building building) {
        buildingService.persist(building);
        LOG.debug("Building with id {} has been created.", building.getId());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", building.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBuilding(@PathVariable int id, @RequestBody Building building) {
        final Building defaultBuilding = buildingService.findById(id);
        if(!defaultBuilding.getId().equals(building.getId())) {
            throw new ValidationException("ID does not match with the requested one.");
        }
        buildingService.update(building);
        LOG.debug("Building with id {} has been updated.", building.getId());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBuilding(@PathVariable int id) {
        final Building building = getBuildingById(id);
        if(building == null) {
            return;
        }

        List<MeetingRoom> meetingRooms = meetingRoomService.findAllMeetingRoomsByBuilding(building.getId());

        for(MeetingRoom room : meetingRooms) {
            List<Reservation> reservations = reservationService.findAllReservationsForMeetingRoomById(room.getId());
            for(Reservation reservation : reservations) {
                reservationService.remove(reservation);
            }
        }

        for(MeetingRoom meetingRoom : meetingRooms) {
            meetingRoomService.remove(meetingRoom);
        }

        buildingService.remove(building);
        LOG.debug("Building with id {} has been deleted, together with its meeting rooms and reservations.", building.getId());
    }
}