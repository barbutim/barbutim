package cz.cvut.kbss.ear.service;

import cz.cvut.kbss.ear.dao.BuildingDao;
import cz.cvut.kbss.ear.model.Building;
import cz.cvut.kbss.ear.model.MeetingRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class BuildingService {

    private final BuildingDao dao;

    @Autowired
    public BuildingService(BuildingDao dao) {
        this.dao = dao;
    }

    @Transactional
    public void addMeetingRoomIntoBuilding(Building building, MeetingRoom meetingRoom) {
        Objects.requireNonNull(building);
        Objects.requireNonNull(meetingRoom);
        building.addMeetingRoom(meetingRoom);
        dao.update(building);
    }

    @Transactional
    public void removeMeetingRoomFromBuilding(Building building, MeetingRoom meetingRoom) {
        Objects.requireNonNull(building);
        Objects.requireNonNull(meetingRoom);
        building.removeMeetingRoom(meetingRoom);
        dao.update(building);
    }

    @Transactional
    public void persist(Building building) {
        Objects.requireNonNull(building);
        dao.persist(building);
    }

    @Transactional
    public void update(Building building) {
        Objects.requireNonNull(building);
        dao.update(building);
    }

    @Transactional
    public void remove(Building building) {
        Objects.requireNonNull(building);
        dao.remove(building);
    }

    @Transactional
    public Building findById(int id) {
        return dao.find(id);
    }

    @Transactional
    public List<Building> findAll() {
        return dao.findAll();
    }
}