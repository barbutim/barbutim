package cz.cvut.kbss.ear.service;

import cz.cvut.kbss.ear.dao.MeetingRoomDao;
import cz.cvut.kbss.ear.model.Equipment;
import cz.cvut.kbss.ear.model.MeetingRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class MeetingRoomService {

    private final MeetingRoomDao dao;

    @Autowired
    public MeetingRoomService(MeetingRoomDao dao) {
        this.dao = dao;
    }

    @Transactional
    public void addEquipmentIntoMeetingRoom(MeetingRoom meetingRoom, Equipment equipment) {
        Objects.requireNonNull(meetingRoom);
        Objects.requireNonNull(equipment);
        meetingRoom.addEquipment(equipment);
        dao.update(meetingRoom);
    }

    @Transactional
    public void addListOfEquipmentsIntoMeetingRoom(MeetingRoom meetingRoom, List<Equipment> equipment) {
        Objects.requireNonNull(meetingRoom);
        Objects.requireNonNull(equipment);
        for (Equipment value : equipment) {
            meetingRoom.addEquipment(value);
        }
        dao.update(meetingRoom);
    }

    @Transactional
    public void removeEquipmentFromMeetingRoom(MeetingRoom meetingRoom, Equipment equipment) {
        Objects.requireNonNull(meetingRoom);
        Objects.requireNonNull(equipment);
        meetingRoom.removeEquipment(equipment);
        dao.update(meetingRoom);
    }

    @Transactional
    public void removeListOfEquipmentsFromMeetingRoom(MeetingRoom meetingRoom, List<Equipment> equipment) {
        Objects.requireNonNull(meetingRoom);
        Objects.requireNonNull(equipment);
        for (Equipment value : equipment) {
            meetingRoom.removeEquipment(value);
        }
        dao.update(meetingRoom);
    }

    @Transactional
    public List<MeetingRoom> getListOfActiveMeetingRooms() {
        return dao.findAllActive();
    }

    @Transactional
    public boolean checkIfMeetingRoomIsActive(MeetingRoom meetingRoom) {
        Objects.requireNonNull(meetingRoom);
        return dao.checkIfActive(meetingRoom.getId()) != null;
    }

    @Transactional
    public List<MeetingRoom> getListOfFullyBookedMeetingRoomsAtDate(LocalDate date) {
        Objects.requireNonNull(date);
        return dao.findFullyBookedMeetingRoomsAtDate(date);
    }

    @Transactional
    public boolean checkIfMeetingRoomIsFullyBookedAtDate(MeetingRoom meetingRoom, LocalDate date) {
        Objects.requireNonNull(meetingRoom);
        Objects.requireNonNull(date);
        return dao.checkIfMeetingRoomsIsFullyBookedAtDate(date, meetingRoom.getId()) != null;
    }

    // Adds notPrioritized MeetingRooms into list of prioritized MeetingRooms, if there is less than 30% available prioritized MeetingRooms.
    @Transactional
    public boolean addNotPrioritizedMeetingRoomsIfNeeded(Integer searchedID, List<MeetingRoom> prioritized, List<MeetingRoom> notPrioritized) {
        if((float) prioritized.size() / dao.findNumberOfPrioritized() < 0.3) {
            prioritized.addAll(notPrioritized);
        }
        for (MeetingRoom meetingRoom : prioritized) {
            if (meetingRoom.getId().equals(searchedID)) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean checkIfMeetingRoomIsAvailableAtDate(MeetingRoom meetingRoom, LocalDate date) {
        Objects.requireNonNull(meetingRoom);
        Objects.requireNonNull(date);
        List<MeetingRoom> prioritizedMeetingRooms = dao.findPrioritizedAvailableMeetingRoomsAtDate(date);
        List<MeetingRoom> notPrioritizedMeetingRooms = dao.findNotPrioritizedAvailableMeetingRoomsAtDate(date);

        return addNotPrioritizedMeetingRoomsIfNeeded(meetingRoom.getId(), prioritizedMeetingRooms, notPrioritizedMeetingRooms);
    }

    @Transactional
    public boolean checkIfMeetingRoomIsAvailableInTheMorningAtDate(MeetingRoom meetingRoom, LocalDate date) {
        Objects.requireNonNull(meetingRoom);
        Objects.requireNonNull(date);
        List<MeetingRoom> prioritizedMeetingRooms = dao.findPrioritizedAvailableMeetingRoomsInTheMorningAtDate(date);
        List<MeetingRoom> notPrioritizedMeetingRooms = dao.findNotPrioritizedAvailableMeetingRoomsInTheMorningAtDate(date);

        return addNotPrioritizedMeetingRoomsIfNeeded(meetingRoom.getId(), prioritizedMeetingRooms, notPrioritizedMeetingRooms);
    }

    @Transactional
    public boolean checkIfMeetingRoomIsAvailableInTheAfternoonAtDate(MeetingRoom meetingRoom, LocalDate date) {
        Objects.requireNonNull(meetingRoom);
        Objects.requireNonNull(date);
        List<MeetingRoom> prioritizedMeetingRooms = dao.findPrioritizedAvailableMeetingRoomsInTheAfternoonAtDate(date);
        List<MeetingRoom> notPrioritizedMeetingRooms = dao.findNotPrioritizedAvailableMeetingRoomsInTheAfternoonAtDate(date);

        return addNotPrioritizedMeetingRoomsIfNeeded(meetingRoom.getId(), prioritizedMeetingRooms, notPrioritizedMeetingRooms);
    }

    @Transactional
    public boolean checkIfMeetingRoomIsAvailableDuringAllDayAtDate(MeetingRoom meetingRoom, LocalDate date) {
        Objects.requireNonNull(meetingRoom);
        Objects.requireNonNull(date);
        List<MeetingRoom> prioritizedMeetingRooms = dao.findPrioritizedAvailableMeetingRoomsDuringAllDayAtDate(date);
        List<MeetingRoom> notPrioritizedMeetingRooms = dao.findNotPrioritizedAvailableMeetingRoomsDuringAllDayAtDate(date);

        return addNotPrioritizedMeetingRoomsIfNeeded(meetingRoom.getId(), prioritizedMeetingRooms, notPrioritizedMeetingRooms);
    }

    @Transactional
    public boolean checkIfMeetingRoomFulFillsAllFilteredCriteria(MeetingRoom meetingRoom, Integer maxPricePerHalfDay, Integer minCapacity) {
        Objects.requireNonNull(meetingRoom);
        Objects.requireNonNull(maxPricePerHalfDay);
        Objects.requireNonNull(minCapacity);
        List<MeetingRoom> prioritizedMeetingRooms = dao.findPrioritizedFilteredMeetingRooms(maxPricePerHalfDay, minCapacity);
        List<MeetingRoom> notPrioritizedMeetingRooms = dao.findNotPrioritizedFilteredMeetingRooms(maxPricePerHalfDay, minCapacity);

        return addNotPrioritizedMeetingRoomsIfNeeded(meetingRoom.getId(), prioritizedMeetingRooms, notPrioritizedMeetingRooms);
    }

    @Transactional
    public List<MeetingRoom> findAllMeetingRoomsByBuilding(Integer id) {
        return dao.findAllMeetingRoomsByBuilding(id);
    }

    @Transactional
    public void persist(MeetingRoom meetingRoom) {
        Objects.requireNonNull(meetingRoom);
        dao.persist(meetingRoom);
    }

    @Transactional
    public void update(MeetingRoom meetingRoom) {
        Objects.requireNonNull(meetingRoom);
        dao.update(meetingRoom);
    }

    @Transactional
    public void remove(MeetingRoom meetingRoom) {
        Objects.requireNonNull(meetingRoom);
        dao.remove(meetingRoom);
    }

    @Transactional
    public MeetingRoom findById(int id) {
        return dao.find(id);
    }

    @Transactional
    public List<MeetingRoom> findAll() {
        return dao.findAll();
    }
}
