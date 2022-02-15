package cz.cvut.kbss.ear.model;

import cz.cvut.kbss.ear.environment.Generator;
import org.junit.Assert;
import org.junit.Test;

public class MeetingRoomTest {

    @Test
    public void addEquipmentAddsEquipmentIntoTheMeetingRoomWithoutAnyEquipment() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoom();
        final Equipment equipment = Generator.generateEquipment();

        meetingRoom.addEquipment(equipment);

        Assert.assertEquals(1, meetingRoom.getEquipmentList().size());
    }

    @Test
    public void addEquipmentAddsEquipmentIntoTheMeetingRoomWithEquipments() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoom();
        final Equipment firstEquipment = Generator.generateEquipment();
        final Equipment secondEquipment = Generator.generateEquipment();

        meetingRoom.addEquipment(firstEquipment);
        meetingRoom.addEquipment(secondEquipment);

        Assert.assertEquals(2, meetingRoom.getEquipmentList().size());
    }

    @Test
    public void removeEquipmentRemovesEquipmentFromTheMeetingRoom() {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoom();
        final Equipment equipment = Generator.generateEquipment();

        meetingRoom.addEquipment(equipment);
        meetingRoom.removeEquipment(equipment);

        Assert.assertEquals(0, meetingRoom.getEquipmentList().size());
    }
}
