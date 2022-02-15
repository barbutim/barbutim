package cz.cvut.kbss.ear.model;

import cz.cvut.kbss.ear.environment.Generator;
import org.junit.Assert;
import org.junit.Test;

public class BuildingTest {

    @Test
    public void addMeetingRoomAddsMeetingRoomIntoTheBuildingWithoutAnyMeetingRooms() {
        final Building building = Generator.generateBuilding();
        final MeetingRoom meetingRoom = Generator.generateMeetingRoom();

        building.addMeetingRoom(meetingRoom);

        Assert.assertEquals(1, building.getMeetingRooms().size());
    }

    @Test
    public void addMeetingRoomAddsMeetingRoomIntoTheBuildingWithMeetingRooms() {
        final Building building = Generator.generateBuilding();
        final MeetingRoom firstRoom = Generator.generateMeetingRoom();
        final MeetingRoom secondRoom = Generator.generateMeetingRoom();

        building.addMeetingRoom(firstRoom);
        building.addMeetingRoom(secondRoom);

        Assert.assertEquals(2, building.getMeetingRooms().size());
    }

    @Test
    public void removeMeetingRoomRemovesMeetingRoomFromTheBuilding() {
        final Building building = Generator.generateBuilding();
        final MeetingRoom meetingRoom = Generator.generateMeetingRoom();

        building.addMeetingRoom(meetingRoom);
        building.removeMeetingRoom(meetingRoom);

        Assert.assertEquals(0, building.getMeetingRooms().size());
    }
}
