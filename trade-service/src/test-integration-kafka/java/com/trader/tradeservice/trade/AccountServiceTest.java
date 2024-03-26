package com.trader.tradeservice.trade;

import com.trader.tradeservice.IntegrationTest;

import com.trader.tradeservice.KafkaProducer;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;
import static org.awaitility.Awaitility.*;

class AccountServiceTest extends IntegrationTest {

    @Value("${starting-balance}")
    private BigDecimal startingBalance;
    @Autowired
    private KafkaProducer testProducer;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    @Transactional
    void createAccountShouldRegisterAccountAfterKafkaEvent()  {
        int sizeBefore = accountRepository.findAll().size();
        UUID userId = UUID.fromString("397bf7cc-0790-45cf-9cd6-d1e9b6cb8ecd");

        testProducer.send("user-created", "{\"id\":\"" + userId + "\",\"email\":\"a@b.com\"}");

        await().atMost(10, TimeUnit.SECONDS).until(() -> accountRepository.getAccountByUserId(userId).isPresent());
        assertThat(accountRepository.findAll().size()).isEqualTo(sizeBefore + 1);
        assertThat(accountRepository.getAccountByUserId(userId).get().getMoney()).isEqualByComparingTo(startingBalance);
    }
}


