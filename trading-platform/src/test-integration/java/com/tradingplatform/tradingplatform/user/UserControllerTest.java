package com.tradingplatform.tradingplatform.user;

import com.tradingplatform.tradingplatform.IntegrationTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends IntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void registerUserShouldCreateNewUser() throws Exception {
        int sizeBefore = userRepository.findAll().size();
        RegisterRequest request = new RegisterRequest("test@email.com", "password123");
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.email").value("test@email.com"));

        assertThat(userRepository.findAll().size()).isEqualTo(sizeBefore + 1);
    }
}