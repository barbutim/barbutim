package cz.cvut.kbss.ear.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import cz.cvut.kbss.ear.environment.Generator;
import cz.cvut.kbss.ear.model.MeetingRoom;
import cz.cvut.kbss.ear.service.MeetingRoomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MeetingRoomControllerTest extends BaseControllerTestRunner {

    @Mock
    private MeetingRoomService meetingRoomServiceMock;

    @InjectMocks
    private MeetingRoomController sut;

    @BeforeEach
    public void setUp() {
        super.setUp(sut);
    }

    @Test
    public void getMeetingRoomsReturnsAllMeetingRooms() throws Exception {
        final List<MeetingRoom> meetingRooms = IntStream.range(0, 15).mapToObj(i -> Generator.generateMeetingRoom()).collect(
                Collectors.toList());
        Mockito.when(meetingRoomServiceMock.findAll()).thenReturn(meetingRooms);
        final MvcResult mvcResult = mockMvc.perform(get("/rest/meetingroom")).andReturn();
        final List<MeetingRoom> result = readValue(mvcResult, new TypeReference<List<MeetingRoom>>() {
        });
        Assertions.assertNotNull(result);
        Assertions.assertEquals(meetingRooms.size(), result.size());
        for (int i = 0; i < meetingRooms.size(); i++) {
            Assertions.assertEquals(meetingRooms.get(i).getId(), result.get(i).getId());
            Assertions.assertEquals(meetingRooms.get(i).getCapacity(), result.get(i).getCapacity());
        }
    }

    @Test
    public void getMeetingRoomByIdReturnsMeetingRoomWithId() throws Exception {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoom();
        meetingRoom.setId(123);
        when(meetingRoomServiceMock.findById(meetingRoom.getId())).thenReturn(meetingRoom);
        final MvcResult mvcResult = mockMvc.perform(get("/rest/meetingroom/" + meetingRoom.getId())).andReturn();
        final MeetingRoom result = readValue(mvcResult, MeetingRoom.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(meetingRoom.getId(), result.getId());
        Assertions.assertEquals(meetingRoom.getCapacity(), result.getCapacity());
        Assertions.assertEquals(meetingRoom.getName(), result.getName());
    }

    /*@Test
    public void removeMeetingRoomRemovesMeetingRoom() throws Exception {
        final MeetingRoom meetingRoom = Generator.generateMeetingRoom();
        meetingRoom.setId(123);

        when(meetingRoomServiceMock.findById(meetingRoom.getId())).thenReturn(meetingRoom);
        mockMvc.perform(delete("/rest/meetingroom/" + meetingRoom.getId())).andExpect(status().isNoContent());
        verify(meetingRoomServiceMock).remove(meetingRoom);
    }*/
}
