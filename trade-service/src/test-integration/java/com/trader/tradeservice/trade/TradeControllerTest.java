package com.trader.tradeservice.trade;

import com.fasterxml.jackson.databind.JsonNode;
import com.trader.tradeservice.IntegrationTest;
import com.trader.tradeservice.shared.CryptoCurrency;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class TradeControllerTest extends IntegrationTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @Transactional
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
        mockMvc.perform(get("/api/trade"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.balance").value(10000.00))
                .andExpect(jsonPath("$.assets.BTC").value(2.5))
                .andExpect(jsonPath("$.assets.ETH").value(11.22));
    }

    @Test
    @Transactional
    @WithUserDetails(value = "test-user@test.com", userDetailsServiceBeanName = "userService")
    void buyShouldChangeAccountBalanceAndRecordHistory() throws Exception {
        TradeOfferRequest tradeOfferRequest = new TradeOfferRequest(CryptoCurrency.BTC, BigDecimal.valueOf(2.5));

        String offerResponse = mockMvc.perform(post("/api/trade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tradeOfferRequest)))
                .andExpect(status().is(201))
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode offerJson = objectMapper.readTree(offerResponse);
        String tradeOfferId = offerJson.get("id").asText();

        mockMvc.perform(post("/api/trade/buy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tradeOfferId\":\"" + tradeOfferId + "\"}"))
                .andExpect(status().is(200));

        mockMvc.perform(get("/api/trade"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.assets.BTC").value(5));

       mockMvc.perform(get("/api/trade-history?start=" + LocalDate.now() + "&end=" + LocalDate.now())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tradeOfferRequest)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.[0].amount").value(2.5))
                .andExpect(jsonPath("$.[0].currency").value("BTC"));
    }


    @Test
    @Transactional
    @WithUserDetails(value = "test-user@test.com", userDetailsServiceBeanName = "userService")
    void sellShouldChangeAccountBalanceAndRecordHistory() throws Exception {
        TradeOfferRequest tradeOfferRequest = new TradeOfferRequest(CryptoCurrency.ETH, BigDecimal.valueOf(2.12));

        String offerResponse = mockMvc.perform(post("/api/trade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tradeOfferRequest)))
                .andExpect(status().is(201))
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode offerJson = objectMapper.readTree(offerResponse);
        String tradeOfferId = offerJson.get("id").asText();

        mockMvc.perform(post("/api/trade/sell")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tradeOfferId\":\"" + tradeOfferId + "\"}"))
                .andExpect(status().is(200));

        mockMvc.perform(get("/api/trade"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.assets.ETH").value(9.1));

        mockMvc.perform(get("/api/trade-history?start=" + LocalDate.now() + "&end=" + LocalDate.now())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tradeOfferRequest)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.[0].amount").value(-2.12))
                .andExpect(jsonPath("$.[0].currency").value("ETH"));
    }
}
