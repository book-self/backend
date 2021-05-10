package xyz.bookself.controllers.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import xyz.bookself.users.domain.User;
import xyz.bookself.users.repository.UserRepository;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private final String apiPrefix = "/v1/users";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    void givenUserExists_whenGetRequestedWithIdOnPath_thenUserShouldBeReturned()
            throws Exception {
        final User userExists = new User();
        final int validUserId = userExists.getId();

        final String jsonContent = xyz.bookself.controllers.user.TestUtilities.toJsonString(userExists);

        when(userRepository.findById(validUserId)).thenReturn(Optional.of(userExists));

        mockMvc.perform(get(apiPrefix + "/" + validUserId))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonContent));
    }

    @Test
    void givenUserCreated_UserShouldBeReturned()
            throws Exception
    {
        final String newUser = "newUser";
        mockMvc.perform(get(apiPrefix + "/" + newUser))
                .andExpect(status().isOk());
    }
}

