package com.tradingplatform.tradingplatform.trade;

import com.tradingplatform.tradingplatform.IntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class TradeControllerTest extends IntegrationTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void registerUserShouldCreateAccountForUser() throws Exception {
        int sizeBefore = accountRepository.findAll().size();

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"email\":\"a@a.com\",\n" +
                                "    \"password\":\"password123\"\n" +
                                "}"))
                .andExpect(status().is(201));

        assertThat(accountRepository.findAll().size()).isEqualTo(sizeBefore + 1);
    }

    @Test
    @WithUserDetails(value = "test-user@test.com", userDetailsServiceBeanName = "userService")
    void getAccountInfoShouldReturnAccountInfoOfLoggedUser() throws Exception {
        //TODO check getTotal
        mockMvc.perform(get("/api/trade"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.userId").value("b0f7d62e-e3c4-41ae-89b0-369b27c735e2"))
                .andExpect(jsonPath("$.balance").value(10000.00))
                .andExpect(jsonPath("$.assets.BTC").value(2.5))
                .andExpect(jsonPath("$.assets.ETH").value(11.22));
    }

    @Test
    @WithUserDetails(value = "test-user@test.com", userDetailsServiceBeanName = "userService")
    void createOfferShouldGenerateNewOffer() {

    }

    @Test
    @WithUserDetails(value = "test-user@test.com", userDetailsServiceBeanName = "userService")
    void buyShouldChangeAccountBalanceAndRecordHistory() {


    }

    @Test
    @WithUserDetails(value = "test-user@test.com", userDetailsServiceBeanName = "userService")
    void sellShouldChangeAccountBalanceAndRecordHistory() {

    }

}
