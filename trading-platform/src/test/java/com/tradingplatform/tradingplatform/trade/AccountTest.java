package com.tradingplatform.tradingplatform.trade;

import com.tradingplatform.tradingplatform.shared.CryptoCurrency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;


class AccountTest {

    private Account account;
    @BeforeEach
    void setUp() {
        account = new Account(UUID.randomUUID(), BigDecimal.valueOf(5000), getTestAsset());
    }

    @Test
    void getTotal_shouldReturnTotal() {
        HashMap<CryptoCurrency, BigDecimal> rates = new HashMap<>();
        rates.put(CryptoCurrency.BTC, BigDecimal.valueOf(10));
        rates.put(CryptoCurrency.ETH, BigDecimal.valueOf(20));
        assertThat(account.getTotal(rates)).isEqualByComparingTo(BigDecimal.valueOf(5069));
    }

    @Test
    void sell_noEnoughCrypto_shouldThrowException() {
        assertThatThrownBy(() -> account.sell(CryptoCurrency.BTC, new BigDecimal("1.6"), BigDecimal.TEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Not enough crypto");
    }
    @Test
    void sell_shouldDecreaseCryptoAmount() {
        account.sell(CryptoCurrency.BTC, new BigDecimal("1.5"), BigDecimal.TEN);
        assertThat(account.getMoney()).isEqualByComparingTo(BigDecimal.valueOf(5015L));
        assertThat(account.getAssets().get(CryptoCurrency.BTC)).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void buy_noEnoughMoney_shouldThrowException() {
        assertThatThrownBy(() -> account.buy(CryptoCurrency.ETH, new BigDecimal(5001L), BigDecimal.ONE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Not enough money to buy");

    }

    @Test
    void buy_shouldIncreaseCryptoAmount() {
        account.buy(CryptoCurrency.ETH, new BigDecimal(2L), BigDecimal.ONE);
        assertThat(account.getMoney()).isEqualByComparingTo(BigDecimal.valueOf(4998L));
        assertThat(account.getAssets().get(CryptoCurrency.ETH)).isEqualByComparingTo(new BigDecimal("4.7"));
    }


    private HashMap<CryptoCurrency, BigDecimal> getTestAsset() {
        HashMap<CryptoCurrency, BigDecimal> asset = new HashMap<>();
        asset.put(CryptoCurrency.BTC, new BigDecimal("1.5"));
        asset.put(CryptoCurrency.ETH, new BigDecimal("2.7"));
        return asset;
    }
}