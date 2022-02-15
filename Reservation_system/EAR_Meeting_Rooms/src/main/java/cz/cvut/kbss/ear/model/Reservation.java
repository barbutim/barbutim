package cz.cvut.kbss.ear.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Reservation.findAll", query = "SELECT r FROM Reservation AS r"),
        @NamedQuery(name = "Reservation.findAllReservationsForMeetingRoomById", query = "SELECT r FROM Reservation AS r WHERE (r.meetingRoom.id = :id)")
})
public class Reservation extends AbstractEntity {

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="meetingRoom_id")
    private MeetingRoom meetingRoom;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Time time;

    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDate dateOfReservation;

    @Basic(optional = false)
    @Column(nullable = false)
    private Integer price;

    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDateTime reservationCreatedOn;

    public Reservation() {
    }

    public Reservation(User user, MeetingRoom meetingRoom, Status status, Time time, LocalDate dateOfReservation, Integer price, LocalDateTime reservationCreatedOn) {
        this.user = user;
        this.meetingRoom = meetingRoom;
        this.status = status;
        this.time = time;
        this.dateOfReservation = dateOfReservation;
        this.price = price;
        this.reservationCreatedOn = reservationCreatedOn;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public LocalDate getDateOfReservation() {
        return dateOfReservation;
    }

    public void setDateOfReservation(LocalDate dateOfReservation) {
        this.dateOfReservation = dateOfReservation;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDateTime getReservationCreatedOn() {
        return reservationCreatedOn;
    }

    public void setReservationCreatedOn(LocalDateTime reservationCreatedOn) {
        this.reservationCreatedOn = reservationCreatedOn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MeetingRoom getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(MeetingRoom meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation reservation = (Reservation) o;
        return getId().equals(reservation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), dateOfReservation, price, reservationCreatedOn);
    }
}
