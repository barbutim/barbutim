package cz.cvut.kbss.ear.environment;

import cz.cvut.kbss.ear.model.Equipment;
import cz.cvut.kbss.ear.model.MeetingRoom;
import cz.cvut.kbss.ear.model.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListGenerator {

    public static List<MeetingRoom> generateListOfMeetingRooms() {
        List<MeetingRoom> meetingRooms = new ArrayList<>();
        Random random = new Random();

        for(int i = 0; i < random.nextInt(10) + 1; i++) {
            meetingRooms.add(Generator.generateMeetingRoom());
        }

        return meetingRooms;
    }

    public static List<Reservation> generateListOfReservations() {
        List<Reservation> reservations = new ArrayList<>();
        Random random = new Random();

        for(int i = 0; i < random.nextInt(10) + 1; i++) {
            reservations.add(Generator.generateReservation());
        }

        return reservations;
    }

    public static List<Equipment> generateListOfEquipments() {
        List<Equipment> equipments = new ArrayList<>();
        Random random = new Random();

        for(int i = 0; i < random.nextInt(10) + 1; i++) {
            equipments.add(Generator.generateEquipment());
        }

        return equipments;
    }
}
