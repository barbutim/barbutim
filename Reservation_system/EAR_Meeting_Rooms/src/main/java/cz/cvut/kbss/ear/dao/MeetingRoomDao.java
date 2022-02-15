package cz.cvut.kbss.ear.dao;

import cz.cvut.kbss.ear.model.*;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class MeetingRoomDao extends BaseDao<MeetingRoom> {

    public MeetingRoomDao() {
        super(MeetingRoom.class);
    }

    public List<MeetingRoom> findAll() {
        return em.createNamedQuery("MeetingRoom.findAll", MeetingRoom.class).getResultList();
    }

    public List<MeetingRoom> findAllActive() {
        try {
            return em.createNamedQuery("MeetingRoom.findAllActive", MeetingRoom.class).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public MeetingRoom checkIfActive(Integer id) {
        try {
            return em.createNamedQuery("MeetingRoom.checkIfActive", MeetingRoom.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public MeetingRoom findByName(String name) {
        try {
            return em.createNamedQuery("MeetingRoom.findByName", MeetingRoom.class).setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<MeetingRoom> findFullyBookedMeetingRoomsAtDate(LocalDate date) {
        try {
            return em.createNamedQuery("MeetingRoom.findFullyBookedMeetingRoomsAtDate", MeetingRoom.class).setParameter("date", date).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public MeetingRoom checkIfMeetingRoomsIsFullyBookedAtDate(LocalDate date, Integer id) {
        try {
            return em.createNamedQuery("MeetingRoom.checkIfMeetingRoomsIsFullyBookedAtDate", MeetingRoom.class).setParameter("date", date).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<MeetingRoom> findPrioritizedAvailableMeetingRoomsAtDate(LocalDate date) {
        try {
            return em.createNamedQuery("MeetingRoom.findPrioritizedAvailableMeetingRoomsAtDate", MeetingRoom.class).setParameter("date", date).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<MeetingRoom> findNotPrioritizedAvailableMeetingRoomsAtDate(LocalDate date) {
        try {
            return em.createNamedQuery("MeetingRoom.findNotPrioritizedAvailableMeetingRoomsAtDate", MeetingRoom.class).setParameter("date", date).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<MeetingRoom> findPrioritizedAvailableMeetingRoomsInTheMorningAtDate(LocalDate date) {
        try {
            return em.createNamedQuery("MeetingRoom.findPrioritizedAvailableMeetingRoomsInTheMorningAtDate", MeetingRoom.class).setParameter("date", date).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<MeetingRoom> findNotPrioritizedAvailableMeetingRoomsInTheMorningAtDate(LocalDate date) {
        try {
            return em.createNamedQuery("MeetingRoom.findNotPrioritizedAvailableMeetingRoomsInTheMorningAtDate", MeetingRoom.class).setParameter("date", date).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<MeetingRoom> findPrioritizedAvailableMeetingRoomsInTheAfternoonAtDate(LocalDate date) {
        try {
            return em.createNamedQuery("MeetingRoom.findPrioritizedAvailableMeetingRoomsInTheAfternoonAtDate", MeetingRoom.class).setParameter("date", date).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<MeetingRoom> findNotPrioritizedAvailableMeetingRoomsInTheAfternoonAtDate(LocalDate date) {
        try {
            return em.createNamedQuery("MeetingRoom.findNotPrioritizedAvailableMeetingRoomsInTheAfternoonAtDate", MeetingRoom.class).setParameter("date", date).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<MeetingRoom> findPrioritizedAvailableMeetingRoomsDuringAllDayAtDate(LocalDate date) {
        try {
            return em.createNamedQuery("MeetingRoom.findPrioritizedAvailableMeetingRoomsDuringAllDayAtDate", MeetingRoom.class).setParameter("date", date).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<MeetingRoom> findNotPrioritizedAvailableMeetingRoomsDuringAllDayAtDate(LocalDate date) {
        try {
            return em.createNamedQuery("MeetingRoom.findNotPrioritizedAvailableMeetingRoomsDuringAllDayAtDate", MeetingRoom.class).setParameter("date", date).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<MeetingRoom> findPrioritizedFilteredMeetingRooms(Integer maxPricePerHalfDay, Integer minCapacity) {
        try {
            return em.createNamedQuery("MeetingRoom.findPrioritizedFilteredMeetingRooms", MeetingRoom.class)
                    .setParameter("maxPricePerHalfDay", maxPricePerHalfDay).setParameter("minCapacity", minCapacity).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<MeetingRoom> findNotPrioritizedFilteredMeetingRooms(Integer maxPricePerHalfDay, Integer minCapacity) {
        try {
            return em.createNamedQuery("MeetingRoom.findNotPrioritizedFilteredMeetingRooms", MeetingRoom.class)
                    .setParameter("maxPricePerHalfDay", maxPricePerHalfDay).setParameter("minCapacity", minCapacity).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public int findNumberOfPrioritized() {
        try {
            return ((Number)em.createNamedQuery("MeetingRoom.findNumberOfPrioritized", Integer.class).getResultList()).intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }

    public int findNumberOfNotPrioritized() {
        try {
            return ((Number)em.createNamedQuery("MeetingRoom.findNumberOfNotPrioritized", Integer.class).getSingleResult()).intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }

    // Meeting Rooms By Building
    public List<MeetingRoom> findAllMeetingRoomsByBuilding(Integer id) {
        try {
            return em.createNamedQuery("Building.findAllMeetingRoomsByBuilding", MeetingRoom.class).setParameter("id", id).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
