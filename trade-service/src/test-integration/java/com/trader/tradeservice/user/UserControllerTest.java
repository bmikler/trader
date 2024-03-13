package com.trader.tradeservice.user;

import com.trader.tradeservice.IntegrationTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends IntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void registerUserShouldCreateNewUserAndAccount() throws Exception {
        int sizeBefore = userRepository.findAll().size();
        RegisterRequest request = new RegisterRequest("new-user@email.com", "password123");
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.email").value("new-user@email.com"));

        assertThat(userRepository.findAll().size()).isEqualTo(sizeBefore + 1);
        AppUser createdUser = userRepository.findByEmail("new-user@email.com").get();

        mockMvc.perform(get("/api/trade").with(user(new SecurityUser(createdUser))))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.balance").value(10000.00))
                .andExpect(jsonPath("$.assets.BTC").value(0.0))
                .andExpect(jsonPath("$.assets.ETH").value(0.0));
    }
}
