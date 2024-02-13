package com.tradingplatform.tradingplatform.trade;


import com.tradingplatform.tradingplatform.rate.CryptoCurrency;
import com.tradingplatform.tradingplatform.rate.Rate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;


import java.math.BigDecimal;
import java.util.*;

@Entity
class Account {

    @Id
    private final UUID id = UUID.randomUUID();
    private final UUID userId;
    @ElementCollection
    @CollectionTable(name = "asset", joinColumns = @JoinColumn(name = "account_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "cryptocurrency")
    @Column(name = "amount")
    private final Map<CryptoCurrency, BigDecimal> assets;
    @Getter
    private BigDecimal money;

    Account(UUID userId, BigDecimal money, Map<CryptoCurrency, BigDecimal> assets) {
        this.userId = userId;
        this.money = money;
        this.assets = assets;
    }

    Map<CryptoCurrency, BigDecimal> getAssets() {
        return Collections.unmodifiableMap(assets);
    }

    BigDecimal getTotal(Map<CryptoCurrency, BigDecimal> rates) {
        return money.add(assets.entrySet().stream()
                .map(entry -> entry.getValue().multiply(rates.get(entry.getKey())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    void sell(CryptoCurrency currency, BigDecimal amountToSell, BigDecimal rate) {
        BigDecimal cryptoAmount = assets.getOrDefault(currency, BigDecimal.ZERO);

        if (cryptoAmount.compareTo(amountToSell) < 0) {
            throw new RuntimeException("Not enough crypto");
        }

        BigDecimal amountToUpdate = assets.get(currency).subtract(amountToSell);
        assets.put(currency, amountToUpdate);
        money = money.add(amountToSell.multiply(rate));
    }

    void buy(CryptoCurrency currency, BigDecimal amountToBuy, BigDecimal rate) {
        BigDecimal moneyNeeded = amountToBuy.multiply(rate);

        if (money.compareTo(moneyNeeded) < 0) {
            throw new RuntimeException("Not enough money to buy");
        }

        money = money.subtract(moneyNeeded);
        assets.put(currency, assets.get(currency).add(amountToBuy));
    }
}
