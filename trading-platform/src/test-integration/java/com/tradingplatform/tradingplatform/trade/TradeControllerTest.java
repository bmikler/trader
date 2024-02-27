package com.tradingplatform.tradingplatform.trade;

import com.tradingplatform.tradingplatform.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;



class TradeControllerTest extends IntegrationTest {

    @Test
    void registerUserShouldCreateAccountForUser() {

    }

    @Test
    void getAccountInfoShouldReturnAccountInfoOfLoggedUser() throws UnsupportedEncodingException {
//        MvcResult result = mockMvc.perform(get("/api/user")
////                        .with(user("test@test.com")))
////                .andReturn();
//
//        String response = result.getResponse().getContentAsString();
//        System.out.println(response);
    }

    @Test
    void createOfferShouldGenerateNewOffer() {

    }

    @Test
    void buyShouldChangeAccountBalance() {


    }

    @Test
    void sellShouldChangeAccountBalance() {

    }

}
