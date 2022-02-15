package cz.cvut.kbss.ear.environment;

import cz.cvut.kbss.ear.model.*;

import javax.annotation.Priority;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class Generator {

    private static final Random RAND = new Random();

    public static int randomInt() {
        return RAND.nextInt();
    }

    public static boolean randomBoolean() {
        return RAND.nextBoolean();
    }

    public static Address generateAddress() {
        final Address address = new Address();

        address.setCity("City" + randomInt());
        address.setStreet("Street" + randomInt());
        address.setHouseNumber("HouseNumber" + randomInt());
        address.setPostcode("PostCode" + randomInt());

        return address;
    }

    public static Building generateBuilding() {
        final Building building = new Building();

        building.setName("Name" + randomInt());
        //building.setMeetingRooms(ListGenerator.generateListOfMeetingRooms());
        building.setAddress(generateAddress());

        return building;
    }

    public static Equipment generateEquipment() {
        final Equipment equipment = new Equipment();

        equipment.setName("Name" + randomInt());

        return equipment;
    }

    public static MeetingRoom generateMeetingRoom() {
        final MeetingRoom meetingRoom = new MeetingRoom();

        meetingRoom.setActive(randomBoolean());
        meetingRoom.setPrioritized(randomBoolean());
        meetingRoom.setName("Name" + randomInt());
        meetingRoom.setCapacity(randomInt());
        meetingRoom.setFloorNumber(randomInt());
        meetingRoom.setPricePerHalfDay(randomInt());
        meetingRoom.setBuilding(generateBuilding());
        //meetingRoom.setEquipmentList(ListGenerator.generateListOfEquipments());

        return meetingRoom;
    }

    public static MeetingRoom generateMeetingRoomForActivityAndPrioritization(boolean active, boolean prioritized) {
        final MeetingRoom meetingRoom = new MeetingRoom();

        meetingRoom.setActive(active);
        meetingRoom.setPrioritized(prioritized);
        meetingRoom.setName("Name" + randomInt());
        meetingRoom.setCapacity(randomInt());
        meetingRoom.setFloorNumber(randomInt());
        meetingRoom.setPricePerHalfDay(randomInt());
        meetingRoom.setBuilding(generateBuilding());
        //meetingRoom.setEquipmentList(ListGenerator.generateListOfEquipments());

        return meetingRoom;
    }

    public static MeetingRoom generateMeetingRoomWithAdjustedVariables(Boolean prioritized, Integer capacity, Integer pricePerHalfDay, Boolean active) {
        final MeetingRoom meetingRoom = new MeetingRoom();

        meetingRoom.setActive(active);
        meetingRoom.setPrioritized(prioritized);
        meetingRoom.setName("Name" + randomInt());
        meetingRoom.setCapacity(capacity);
        meetingRoom.setFloorNumber(randomInt());
        meetingRoom.setPricePerHalfDay(pricePerHalfDay);
        meetingRoom.setBuilding(Generator.generateBuilding());
        //meetingRoom.setEquipmentList(ListGenerator.generateListOfEquipments());

        return meetingRoom;
    }

    public static Reservation generateReservation() {
        final Reservation reservation = new Reservation();

        reservation.setStatus(EnumGenerator.generateStatus());
        reservation.setTime(EnumGenerator.generateTime());
        reservation.setDateOfReservation(DateTimeGenerator.generateDate());
        reservation.setPrice(randomInt());
        reservation.setReservationCreatedOn(DateTimeGenerator.generateDateTime());
        reservation.setUser(generateUser());
        reservation.setMeetingRoom(generateMeetingRoom());

        return reservation;
    }

    public static Reservation generateReservationForUserWithStatus(User user, Status status) {
        final Reservation reservation = new Reservation();

        reservation.setStatus(status);
        reservation.setTime(EnumGenerator.generateTime());
        reservation.setDateOfReservation(DateTimeGenerator.generateDate());
        reservation.setPrice(randomInt());
        reservation.setReservationCreatedOn(DateTimeGenerator.generateDateTime());
        reservation.setUser(user);
        reservation.setMeetingRoom(generateMeetingRoom());

        return reservation;
    }

    public static Reservation generateReservationForMeetingRoom(MeetingRoom meetingRoom) {
        final Reservation reservation = new Reservation();

        reservation.setStatus(EnumGenerator.generateStatus());
        reservation.setTime(EnumGenerator.generateTime());
        reservation.setDateOfReservation(DateTimeGenerator.generateDate());
        reservation.setPrice(randomInt());
        reservation.setReservationCreatedOn(DateTimeGenerator.generateDateTime());
        reservation.setUser(generateUser());
        reservation.setMeetingRoom(meetingRoom);

        return reservation;
    }

    public static Reservation generateReservationForMeetingRoomAtDateAndTimeWithStatus(MeetingRoom meetingRoom, LocalDate date, Time time, Status status) {
        final Reservation reservation = new Reservation();

        reservation.setStatus(status);
        reservation.setTime(time);
        reservation.setDateOfReservation(date);
        reservation.setPrice(randomInt());
        reservation.setReservationCreatedOn(DateTimeGenerator.generateDateTime());
        reservation.setUser(generateUser());
        reservation.setMeetingRoom(meetingRoom);

        return reservation;
    }

    public static User generateUser() {
        final User user = new User();

        user.setRole(EnumGenerator.generateRole());
        user.setEmail("Email" + randomInt() + "@kbss.ear.cvut.cz");
        user.setUsername("Username" + randomInt());
        user.setFirstname("Firstname" + randomInt());
        user.setLastName("Lastname" + randomInt());
        user.setPassword(Integer.toString(randomInt()));
        user.setAddress(generateAddress());

        return user;
    }
}
