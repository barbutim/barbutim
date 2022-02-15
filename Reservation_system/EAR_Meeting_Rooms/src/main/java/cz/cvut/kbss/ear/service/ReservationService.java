package cz.cvut.kbss.ear.service;

import cz.cvut.kbss.ear.dao.MeetingRoomDao;
import cz.cvut.kbss.ear.dao.ReservationDao;
import cz.cvut.kbss.ear.exception.MeetingRoomIsNotAvailableException;
import cz.cvut.kbss.ear.model.Reservation;
import cz.cvut.kbss.ear.model.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ReservationService {

    private final ReservationDao dao;

    @Autowired
    public ReservationService(ReservationDao dao) {
        this.dao = dao;
    }

    @Transactional
    public boolean checkIfReservationDoesNotCollideWithOthers(Reservation reservation) {
        Objects.requireNonNull(reservation);
        List<Reservation> reservations = dao.findAllReservationsForMeetingRoomById(reservation.getMeetingRoom().getId());
        for (Reservation value : reservations) {
            if (value.getDateOfReservation() == reservation.getDateOfReservation()) {
                if (value.getTime() == Time.ALLDAY || (value.getTime() == Time.MORNING && reservation.getTime() == Time.MORNING)
                        || (value.getTime() == Time.AFTERNOON && reservation.getTime() == Time.AFTERNOON)) {
                    if (!value.getId().equals(reservation.getId())) {
                        throw new MeetingRoomIsNotAvailableException("Meeting room is not available at this day and time block.");
                    }
                }
            }
        }
        return true;
    }

    @Transactional
    public List<Reservation> findAllReservationsForMeetingRoomById(Integer id) {
        return dao.findAllReservationsForMeetingRoomById(id);
    }

    @Transactional
    public void persist(Reservation reservation) {
        Objects.requireNonNull(reservation);
        dao.persist(reservation);
    }

    @Transactional
    public void update(Reservation reservation) {
        Objects.requireNonNull(reservation);
        dao.update(reservation);
    }

    @Transactional
    public void remove(Reservation reservation) {
        Objects.requireNonNull(reservation);
        dao.remove(reservation);
    }

    @Transactional
    public Reservation findById(int id) {
        return dao.find(id);
    }

    @Transactional
    public List<Reservation> findAll() {
        return dao.findAll();
    }
}