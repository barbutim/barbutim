package cz.cvut.kbss.ear.model;

import javax.persistence.*;
import java.util.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "MeetingRoom.findAll", query = "SELECT m FROM MeetingRoom AS m"),
        @NamedQuery(name = "MeetingRoom.findAllActive", query = "SELECT m FROM MeetingRoom AS m WHERE m.active = TRUE"),
        @NamedQuery(name = "MeetingRoom.checkIfActive", query = "SELECT m FROM MeetingRoom AS m WHERE m.active = TRUE AND m.id = :id"),
        @NamedQuery(name = "MeetingRoom.findByName", query = "SELECT m FROM MeetingRoom AS m WHERE m.name = :name"),
        // Meeting rooms are fully booked at date, for either ALLDAY, or both MORNING and AFTERNOON time.
        @NamedQuery(name = "MeetingRoom.findFullyBookedMeetingRoomsAtDate",
                query = "SELECT r.meetingRoom FROM Reservation AS r LEFT JOIN MeetingRoom AS m ON (m.id = r.id)"
                        + " WHERE (r.status = cz.cvut.kbss.ear.model.Status.ACTIVE OR r.status = cz.cvut.kbss.ear.model.Status.PAID)"
                        + " AND r.dateOfReservation = :date AND ((r.time = cz.cvut.kbss.ear.model.Time.MORNING"
                        + " AND r.time = cz.cvut.kbss.ear.model.Time.AFTERNOON) OR r.time = cz.cvut.kbss.ear.model.Time.ALLDAY)"),
        @NamedQuery(name = "MeetingRoom.checkIfMeetingRoomsIsFullyBookedAtDate",
                query = "SELECT r.meetingRoom FROM Reservation AS r LEFT JOIN MeetingRoom AS m ON (m.id = r.id)"
                        + " WHERE m.id = :id AND ((r.status = cz.cvut.kbss.ear.model.Status.ACTIVE OR r.status = cz.cvut.kbss.ear.model.Status.PAID)"
                        + " AND r.dateOfReservation = :date AND ((r.time = cz.cvut.kbss.ear.model.Time.MORNING"
                        + " AND r.time = cz.cvut.kbss.ear.model.Time.AFTERNOON) OR r.time = cz.cvut.kbss.ear.model.Time.ALLDAY))"),
        // SEARCHING FOR ACTIVE MEETING ROOMS ONLY, WITH AND WITHOUT PRIORITIZATION. FROM NOW ON.
        // Meeting rooms are available at date, for either ALLDAY, or at least MORNING or AFTERNOON time.
        // Therefore, the Reservation Status has to be either ACTIVE or PAID, for reservation to be taking place.
        @NamedQuery(name = "MeetingRoom.findPrioritizedAvailableMeetingRoomsAtDate",
                query = "SELECT r.meetingRoom FROM Reservation AS r LEFT JOIN MeetingRoom AS m ON (m.id = r.id)"
                        + " WHERE r.meetingRoom.active = true AND r.meetingRoom.prioritized = true AND (((r.dateOfReservation = :date AND"
                        + " (r.status = cz.cvut.kbss.ear.model.Status.ACTIVE OR r.status = cz.cvut.kbss.ear.model.Status.PAID)) AND NOT"
                        + " (r.time = cz.cvut.kbss.ear.model.Time.MORNING AND r.time = cz.cvut.kbss.ear.model.Time.AFTERNOON)"
                        + " AND NOT r.time = cz.cvut.kbss.ear.model.Time.ALLDAY) OR NOT r.dateOfReservation = :date)"),
        @NamedQuery(name = "MeetingRoom.findNotPrioritizedAvailableMeetingRoomsAtDate",
                query = "SELECT r.meetingRoom FROM Reservation AS r LEFT JOIN MeetingRoom AS m ON (m.id = r.id)"
                        + " WHERE r.meetingRoom.active = true AND r.meetingRoom.prioritized = false AND (((r.dateOfReservation = :date AND"
                        + " (r.status = cz.cvut.kbss.ear.model.Status.ACTIVE OR r.status = cz.cvut.kbss.ear.model.Status.PAID)) AND NOT"
                        + " (r.time = cz.cvut.kbss.ear.model.Time.MORNING AND r.time = cz.cvut.kbss.ear.model.Time.AFTERNOON)"
                        + " AND NOT r.time = cz.cvut.kbss.ear.model.Time.ALLDAY) OR NOT r.dateOfReservation = :date)"),
        // Meeting rooms are available at date, for either ALLDAY, or at least MORNING time.
        // Therefore, the Reservation Status has to be either ACTIVE or PAID, for reservation to be taking place.
        @NamedQuery(name = "MeetingRoom.findPrioritizedAvailableMeetingRoomsInTheMorningAtDate",
                query = "SELECT r.meetingRoom FROM Reservation AS r LEFT JOIN MeetingRoom AS m ON (m.id = r.id)"
                        + " WHERE r.meetingRoom.active = true AND r.meetingRoom.prioritized = true AND (((r.dateOfReservation = :date AND"
                        + " (r.status = cz.cvut.kbss.ear.model.Status.ACTIVE OR r.status = cz.cvut.kbss.ear.model.Status.PAID)) AND NOT"
                        + " r.time = cz.cvut.kbss.ear.model.Time.MORNING AND NOT r.time = cz.cvut.kbss.ear.model.Time.ALLDAY) OR NOT"
                        + " r.dateOfReservation = :date)"),
        @NamedQuery(name = "MeetingRoom.findNotPrioritizedAvailableMeetingRoomsInTheMorningAtDate",
                query = "SELECT r.meetingRoom FROM Reservation AS r LEFT JOIN MeetingRoom AS m ON (m.id = r.id)"
                        + " WHERE r.meetingRoom.active = true AND r.meetingRoom.prioritized = false AND (((r.dateOfReservation = :date AND"
                        + " (r.status = cz.cvut.kbss.ear.model.Status.ACTIVE OR r.status = cz.cvut.kbss.ear.model.Status.PAID)) AND NOT"
                        + " r.time = cz.cvut.kbss.ear.model.Time.MORNING AND NOT r.time = cz.cvut.kbss.ear.model.Time.ALLDAY) OR NOT"
                        + " r.dateOfReservation = :date)"),
        // Meeting rooms are available at date, for either ALLDAY, or at least AFTERNOON time.
        // Therefore, the Reservation Status has to be either ACTIVE or PAID, for reservation to be taking place.
        @NamedQuery(name = "MeetingRoom.findPrioritizedAvailableMeetingRoomsInTheAfternoonAtDate",
                query = "SELECT r.meetingRoom FROM Reservation AS r LEFT JOIN MeetingRoom AS m ON (m.id = r.id)"
                        + " WHERE r.meetingRoom.active = true AND r.meetingRoom.prioritized = true AND (((r.dateOfReservation = :date AND"
                        + " (r.status = cz.cvut.kbss.ear.model.Status.ACTIVE OR r.status = cz.cvut.kbss.ear.model.Status.PAID)) AND NOT"
                        + " r.time = cz.cvut.kbss.ear.model.Time.AFTERNOON AND NOT r.time = cz.cvut.kbss.ear.model.Time.ALLDAY) OR NOT"
                        + " r.dateOfReservation = :date)"),
        @NamedQuery(name = "MeetingRoom.findNotPrioritizedAvailableMeetingRoomsInTheAfternoonAtDate",
                query = "SELECT r.meetingRoom FROM Reservation AS r LEFT JOIN MeetingRoom AS m ON (m.id = r.id)"
                        + " WHERE r.meetingRoom.active = true AND r.meetingRoom.prioritized = false AND (((r.dateOfReservation = :date AND"
                        + " (r.status = cz.cvut.kbss.ear.model.Status.ACTIVE OR r.status = cz.cvut.kbss.ear.model.Status.PAID)) AND NOT"
                        + " r.time = cz.cvut.kbss.ear.model.Time.AFTERNOON AND NOT r.time = cz.cvut.kbss.ear.model.Time.ALLDAY) OR NOT"
                        + " r.dateOfReservation = :date)"),
        // Meeting rooms are available at date, for ALLDAY only.
        // Therefore, the Reservation Status has to be either ACTIVE or PAID, for reservation to be taking place.
        @NamedQuery(name = "MeetingRoom.findPrioritizedAvailableMeetingRoomsDuringAllDayAtDate",
                query = "SELECT r.meetingRoom FROM Reservation AS r LEFT JOIN MeetingRoom AS m ON (m.id = r.id)"
                        + " WHERE r.meetingRoom.active = true AND r.meetingRoom.prioritized = true AND (((r.dateOfReservation = :date AND"
                        + " (r.status = cz.cvut.kbss.ear.model.Status.ACTIVE OR r.status = cz.cvut.kbss.ear.model.Status.PAID)) AND NOT"
                        + " (r.time = cz.cvut.kbss.ear.model.Time.MORNING OR r.time = cz.cvut.kbss.ear.model.Time.AFTERNOON)"
                        + " AND NOT r.time = cz.cvut.kbss.ear.model.Time.ALLDAY) OR NOT r.dateOfReservation = :date)"),
        @NamedQuery(name = "MeetingRoom.findNotPrioritizedAvailableMeetingRoomsDuringAllDayAtDate",
                query = "SELECT r.meetingRoom FROM Reservation AS r LEFT JOIN MeetingRoom AS m ON (m.id = r.id)"
                        + " WHERE r.meetingRoom.active = true AND r.meetingRoom.prioritized = false AND (((r.dateOfReservation = :date AND"
                        + " (r.status = cz.cvut.kbss.ear.model.Status.ACTIVE OR r.status = cz.cvut.kbss.ear.model.Status.PAID)) AND NOT"
                        + " (r.time = cz.cvut.kbss.ear.model.Time.MORNING OR r.time = cz.cvut.kbss.ear.model.Time.AFTERNOON)"
                        + " AND NOT r.time = cz.cvut.kbss.ear.model.Time.ALLDAY) OR NOT r.dateOfReservation = :date)"),
        // Meeting rooms are filtered by maxPrice and minCapacity.
        @NamedQuery(name = "MeetingRoom.findPrioritizedFilteredMeetingRooms",
                query = "SELECT m FROM MeetingRoom AS m WHERE m.pricePerHalfDay <= :maxPricePerHalfDay"
                        + " AND m.prioritized = true AND m.capacity >= :minCapacity AND m.active = true"),
        @NamedQuery(name = "MeetingRoom.findNotPrioritizedFilteredMeetingRooms",
                query = "SELECT m FROM MeetingRoom AS m WHERE m.pricePerHalfDay <= :maxPricePerHalfDay"
                        + " AND m.prioritized = false AND m.capacity >= :minCapacity AND m.active = true"),
        // Meeting rooms are filtered by prioritization.
        @NamedQuery(name = "MeetingRoom.findNumberOfPrioritized", query = "SELECT COUNT(m) FROM MeetingRoom AS m WHERE m.active = true AND m.prioritized = true"),
        @NamedQuery(name = "MeetingRoom.findNumberOfNotPrioritized", query = "SELECT COUNT(m) FROM MeetingRoom AS m WHERE m.active = true AND m.prioritized = false"),
        // Find MeetingRooms By Building
        @NamedQuery(name = "Building.findAllMeetingRoomsByBuilding", query = "SELECT b.meetingRooms FROM Building AS b LEFT JOIN MeetingRoom AS m ON (m.id = b.id) WHERE b.id = :id")
})
public class MeetingRoom extends AbstractEntity {

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="building_id")
    private Building building;

    @ManyToMany
    @JoinTable(name = "MEET_EQUIP",
            joinColumns =
            @JoinColumn(name = "meetingRoom_id"),
            inverseJoinColumns =
            @JoinColumn(name = "equipment_id"))
    @OrderBy("name DESC")
    private Collection<Equipment> equipmentList;

    @Basic(optional = false)
    @Column(nullable = false)
    private boolean active;

    @Basic(optional = false)
    @Column(nullable = false)
    private boolean prioritized;

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(nullable = false)
    private Integer capacity;

    @Basic(optional = false)
    @Column(nullable = false)
    private Integer floorNumber;

    @Basic(optional = false)
    @Column(nullable = false)
    private Integer pricePerHalfDay;

    public void addEquipment(Equipment equipment) {
        Objects.requireNonNull(equipment);

        if (equipmentList == null) {
            this.equipmentList = new ArrayList<>();
        }
        equipmentList.add(equipment);
    }

    public void removeEquipment(Equipment equipment) {
        Objects.requireNonNull(equipment);

        if (equipmentList == null) {
            return;
        }
        equipmentList.removeIf(c -> Objects.equals(c.getId(), equipment.getId()));

    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPrioritized() {
        return prioritized;
    }

    public void setPrioritized(boolean prioritized) {
        this.prioritized = prioritized;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Integer getPricePerHalfDay() {
        return pricePerHalfDay;
    }

    public void setPricePerHalfDay(Integer pricePerHalfDay) {
        this.pricePerHalfDay = pricePerHalfDay;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Collection<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(Collection<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }
}
