package com.tradingplatform.tradingplatform;

import com.tradingplatform.tradingplatform.TradingPlatformApplication;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TradingPlatformApplication.class)
@AutoConfigureMockMvc
public class IntegrationTest {

    @Test
    public void test() {
        System.err.println("TEST");
    }

}
