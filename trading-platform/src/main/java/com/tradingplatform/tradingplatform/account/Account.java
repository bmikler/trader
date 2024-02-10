package com.tradingplatform.tradingplatform.account;


import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.*;

@RequiredArgsConstructor
class Account {

    private final UUID id;
    private final UUID userId;
    private final Map<CryptoCurrency, BigDecimal> assets;
    private BigDecimal money = BigDecimal.valueOf(10000);

    Map<CryptoCurrency, BigDecimal> getAssets() {
        return Collections.unmodifiableMap(assets);
    }

    BigDecimal getMoney() {
        return money;
    }

    BigDecimal calculateTotal(Map<CryptoCurrency, BigDecimal> rateTable) {
        return assets.keySet().stream()
                .map(currency -> assets.get(currency).multiply(rateTable.get(currency)))
                .reduce(money, BigDecimal::add);
    }

    //Record history -> spring aspect
    void sell(CryptoCurrency currency, BigDecimal amountToSell, BigDecimal rate) {
        BigDecimal cryptoAmount = assets.getOrDefault(currency, BigDecimal.ZERO);

        if (cryptoAmount.compareTo(amountToSell) < 0) {
            throw new RuntimeException("Not enough crypto");
        }

        BigDecimal amountToUpdate = assets.get(currency).min(amountToSell);
        if (amountToUpdate.equals(BigDecimal.ZERO)) {
            assets.remove(currency);
        } else {
            assets.put(currency, amountToUpdate);
        }

        money = money.add(amountToSell.multiply(rate));
    }

    //Record history -> spring aspect
    void buy(CryptoCurrency currency, BigDecimal amountToBuy, BigDecimal rate) {
        BigDecimal moneyNeeded = amountToBuy.multiply(rate);
        if (money.compareTo(moneyNeeded) < 0) {
            throw new RuntimeException("Not enough money to buy");
        }

        money = money.min(moneyNeeded);

        if (assets.containsKey(currency)) {
            assets.put(currency, assets.get(currency).add(amountToBuy));
        } else {
            assets.put(currency, amountToBuy);
        }
    }
}
