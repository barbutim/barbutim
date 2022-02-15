package cz.cvut.kbss.ear.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import cz.cvut.kbss.ear.environment.Generator;
import cz.cvut.kbss.ear.model.User;
import cz.cvut.kbss.ear.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
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
public class UserControllerTest extends BaseControllerTestRunner {

    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private UserController sut;

    @BeforeEach
    public void setUp() {
        super.setUp(sut);
    }

    @Test
    public void getUsersReturnsAllUsers() throws Exception {
        final List<User> users = IntStream.range(0, 15).mapToObj(i -> Generator.generateUser()).collect(
                Collectors.toList());
        Mockito.when(userServiceMock.findAll()).thenReturn(users);
        final MvcResult mvcResult = mockMvc.perform(get("/rest/user")).andReturn();
        final List<User> result = readValue(mvcResult, new TypeReference<List<User>>() {
        });
        Assertions.assertNotNull(result);
        Assertions.assertEquals(users.size(), result.size());
        for (int i = 0; i < users.size(); i++) {
            Assertions.assertEquals(users.get(i).getFirstname(), result.get(i).getFirstname());
            Assertions.assertEquals(users.get(i).getUsername(), result.get(i).getUsername());
        }
    }

    @Test
    public void getUserByIdReturnsUserWithId() throws Exception {
        final User user = Generator.generateUser();
        user.setId(123);
        when(userServiceMock.findById(user.getId())).thenReturn(user);
        final MvcResult mvcResult = mockMvc.perform(get("/rest/user/" + user.getId())).andReturn();
        final User result = readValue(mvcResult, User.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(user.getId(), result.getId());
        Assertions.assertEquals(user.getUsername(), result.getUsername());
        Assertions.assertEquals(user.getFirstname(), result.getFirstname());
    }

    @Test
    public void removeUserRemovesUser() throws Exception {
        final User user = Generator.generateUser();
        user.setId(123);

        when(userServiceMock.findById(user.getId())).thenReturn(user);
        mockMvc.perform(delete("/rest/user/" + user.getId())).andExpect(status().isNoContent());
        verify(userServiceMock).remove(user);
    }
}
