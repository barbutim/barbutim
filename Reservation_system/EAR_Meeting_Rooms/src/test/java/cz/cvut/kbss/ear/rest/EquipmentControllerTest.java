package cz.cvut.kbss.ear.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import cz.cvut.kbss.ear.environment.Generator;
import cz.cvut.kbss.ear.model.Equipment;
import cz.cvut.kbss.ear.service.EquipmentService;
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
public class EquipmentControllerTest extends BaseControllerTestRunner {

    @Mock
    private EquipmentService equipmentServiceMock;

    @InjectMocks
    private EquipmentController sut;

    @BeforeEach
    public void setUp() {
        super.setUp(sut);
    }

    @Test
    public void getEquipmentsReturnsAllEquipments() throws Exception {
        final List<Equipment> equipment = IntStream.range(0, 15).mapToObj(i -> Generator.generateEquipment()).collect(
                Collectors.toList());
        Mockito.when(equipmentServiceMock.findAll()).thenReturn(equipment);
        final MvcResult mvcResult = mockMvc.perform(get("/rest/equipment")).andReturn();
        final List<Equipment> result = readValue(mvcResult, new TypeReference<List<Equipment>>() {
        });
        Assertions.assertNotNull(result);
        Assertions.assertEquals(equipment.size(), result.size());
        for (int i = 0; i < equipment.size(); i++) {
            Assertions.assertEquals(equipment.get(i).getId(), result.get(i).getId());
            Assertions.assertEquals(equipment.get(i).getName(), result.get(i).getName());
        }
    }

    @Test
    public void getEquipmentByIdReturnsEquipmentWithId() throws Exception {
        final Equipment equipment = Generator.generateEquipment();
        equipment.setId(123);
        when(equipmentServiceMock.findById(equipment.getId())).thenReturn(equipment);
        final MvcResult mvcResult = mockMvc.perform(get("/rest/equipment/" + equipment.getId())).andReturn();
        final Equipment result = readValue(mvcResult, Equipment.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(equipment.getId(), result.getId());
        Assertions.assertEquals(equipment.getName(), result.getName());
    }

    @Test
    public void removeEquipmentRemovesEquipment() throws Exception {
        final Equipment equipment = Generator.generateEquipment();
        equipment.setId(123);

        when(equipmentServiceMock.findById(equipment.getId())).thenReturn(equipment);
        mockMvc.perform(delete("/rest/equipment/" + equipment.getId())).andExpect(status().isNoContent());
        verify(equipmentServiceMock).remove(equipment);
    }
}
