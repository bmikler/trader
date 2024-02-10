package com.tradingplatform.tradingplatform.account;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;
import java.util.Optional;

interface RateRepository {

    Optional<BigDecimal> getPrice(CryptoCurrency currency);

    Map<CryptoCurrency, BigDecimal> getRateTable();

}
