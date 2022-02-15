package cz.cvut.kbss.ear.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import cz.cvut.kbss.ear.environment.Generator;
import cz.cvut.kbss.ear.model.Building;
import cz.cvut.kbss.ear.service.BuildingService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BuildingControllerTest extends BaseControllerTestRunner {

    @Mock
    private BuildingService buildingServiceMock;

    @InjectMocks
    private BuildingController sut;

    @BeforeEach
    public void setUp() {
        super.setUp(sut);
    }

    @Test
    public void getBuildingsReturnsAllBuildings() throws Exception {
        final List<Building> buildings = IntStream.range(0, 15).mapToObj(i -> Generator.generateBuilding()).collect(
                Collectors.toList());
        Mockito.when(buildingServiceMock.findAll()).thenReturn(buildings);
        final MvcResult mvcResult = mockMvc.perform(get("/rest/building")).andReturn();
        final List<Building> result = readValue(mvcResult, new TypeReference<List<Building>>() {
        });
        Assertions.assertNotNull(result);
        Assertions.assertEquals(buildings.size(), result.size());
        for (int i = 0; i < buildings.size(); i++) {
            Assertions.assertEquals(buildings.get(i).getId(), result.get(i).getId());
            Assertions.assertEquals(buildings.get(i).getMeetingRooms(), result.get(i).getMeetingRooms());
        }
    }

    @Test
    public void getBuildingByIdReturnsBuildingWithId() throws Exception {
        final Building building = Generator.generateBuilding();
        building.setId(123);
        when(buildingServiceMock.findById(building.getId())).thenReturn(building);
        final MvcResult mvcResult = mockMvc.perform(get("/rest/building/" + building.getId())).andReturn();
        final Building result = readValue(mvcResult, Building.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(building.getId(), result.getId());
        Assertions.assertEquals(building.getName(), result.getName());
    }

    /*@Test
    public void removeBuildingRemovesBuilding() throws Exception {
        final Building building = Generator.generateBuilding();
        building.setId(123);

        when(buildingServiceMock.findById(building.getId())).thenReturn(building);
        mockMvc.perform(delete("/rest/building/" + building.getId())).andExpect(status().isNoContent());
        verify(buildingServiceMock).remove(building);
    }*/
}
