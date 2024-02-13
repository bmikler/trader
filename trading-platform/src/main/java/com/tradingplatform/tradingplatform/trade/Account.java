package com.tradingplatform.tradingplatform.trade;


import com.tradingplatform.tradingplatform.rate.CryptoCurrency;
import com.tradingplatform.tradingplatform.rate.Rate;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.*;

@RequiredArgsConstructor
@Entity
class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private final UUID id;
    private final UUID userId;
    @ElementCollection
    @CollectionTable(name = "asset", joinColumns = @JoinColumn(name = "account_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "cryptocurrency")
    @Column(name = "amount")
    private final Map<CryptoCurrency, BigDecimal> assets;
    private BigDecimal money = BigDecimal.valueOf(10000);

    Map<CryptoCurrency, BigDecimal> getAssets() {
        return Collections.unmodifiableMap(assets);
    }

    BigDecimal getMoney() {
        return money;
    }

    BigDecimal calculateTotal(List<Rate> rateTable) {
        return rateTable.stream()
                .filter(rate -> assets.containsKey(rate.currency()))
                .map(rate -> rate.value().multiply(assets.get(rate.currency())))
                .reduce(money, BigDecimal::add);
    }

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
