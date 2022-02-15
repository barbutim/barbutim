package cz.cvut.kbss.ear.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Building.findAll", query = "SELECT b FROM Building AS b"),
        @NamedQuery(name = "Building.findByName", query = "SELECT b FROM Building AS b WHERE b.name = :name")
})
public class Building extends AbstractEntity {

    @OneToMany(mappedBy = "building", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @OrderBy("capacity ASC")
    private List<MeetingRoom> meetingRooms;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    private Address address;

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    public void addMeetingRoom(MeetingRoom meetingRoom) {
        Objects.requireNonNull(meetingRoom);

        if (meetingRooms == null) {
            this.meetingRooms = new ArrayList<>();
        }
        meetingRooms.add(meetingRoom);
    }

    public void removeMeetingRoom(MeetingRoom meetingRoom) {
        Objects.requireNonNull(meetingRoom);

        if (meetingRooms == null) {
            return;
        }
        meetingRooms.removeIf(c -> Objects.equals(c.getId(), meetingRoom.getId()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MeetingRoom> getMeetingRooms() {
        return meetingRooms;
    }

    public void setMeetingRooms(List<MeetingRoom> meetingRooms) {
        this.meetingRooms = meetingRooms;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
