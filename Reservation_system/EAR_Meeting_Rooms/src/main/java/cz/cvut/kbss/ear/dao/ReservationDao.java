package cz.cvut.kbss.ear.dao;

import cz.cvut.kbss.ear.model.Reservation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReservationDao extends BaseDao<Reservation> {

    public ReservationDao() {
        super(Reservation.class);
    }

    public List<Reservation> findAll() {
        return em.createNamedQuery("Reservation.findAll", Reservation.class).getResultList();
    }

    public List<Reservation> findAllReservationsForMeetingRoomById(Integer id) {
        return em.createNamedQuery("Reservation.findAllReservationsForMeetingRoomById", Reservation.class).setParameter("id", id).getResultList();
    }
}