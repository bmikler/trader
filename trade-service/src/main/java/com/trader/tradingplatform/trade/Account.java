package com.trader.tradingplatform.trade;


import com.trader.tradingplatform.shared.CryptoCurrency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "account")
@NoArgsConstructor
class Account {

    @Id
    @Column(name = "id")
    private final UUID id = UUID.randomUUID();
    @Column(name = "user_id")
    private UUID userId;
    @ElementCollection
    @CollectionTable(name = "asset", joinColumns = @JoinColumn(name = "account_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "cryptocurrency")
    @Column(name = "amount")
    private Map<CryptoCurrency, BigDecimal> assets;
    @Getter
    @Column(name = "money")
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
            throw new IllegalArgumentException("Not enough crypto");
        }

        BigDecimal amountToUpdate = assets.get(currency).subtract(amountToSell);
        assets.put(currency, amountToUpdate);
        money = money.add(amountToSell.multiply(rate));
    }

    void buy(CryptoCurrency currency, BigDecimal amountToBuy, BigDecimal rate) {
        BigDecimal moneyNeeded = amountToBuy.multiply(rate);

        if (money.compareTo(moneyNeeded) < 0) {
            throw new IllegalArgumentException("Not enough money to buy");
        }

        money = money.subtract(moneyNeeded);
        assets.put(currency, assets.get(currency).add(amountToBuy));
    }
}
