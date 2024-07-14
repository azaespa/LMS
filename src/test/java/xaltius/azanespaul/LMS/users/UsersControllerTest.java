package xaltius.azanespaul.LMS.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.print.attribute.standard.Media;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UsersControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UsersService usersService;

    List<Users> usersList;

    @BeforeEach
    void setUp() {
        Users u1 = new Users();
        u1.setId(1L);
        u1.setName("Rakesh Kumar");

        Users u2 = new Users();
        u2.setId(2L);
        u2.setName("Paul Azanes");

        Users u3 = new Users();
        u3.setId(3L);
        u3.setName("Jimbo Delfin");

        this.usersList = new ArrayList<>();
        this.usersList.add(u1);
        this.usersList.add(u2);
        this.usersList.add(u3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testSaveUserSuccess() throws Exception {
        // Given
        Map<String, Object> body = new HashMap<>();
        body.put("id", 1L);
        body.put("name", "Test Case");

        // When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllUsersSuccess() throws Exception {
        // Given
        BDDMockito.given(this.usersService.findAllUsers()).willReturn(this.usersList);

        // When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(this.usersList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Rakesh Kumar"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Paul Azanes"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value("3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("Jimbo Delfin"));
    }
}