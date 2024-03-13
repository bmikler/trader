package com.trader.tradingplatform.rate;


import com.trader.tradingplatform.shared.CryptoCurrency;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

interface RateRepository {

    void updateRates(List<Rate> rates);
    Optional<BigDecimal> getPrice(CryptoCurrency currency);
    List<Rate> getAll();

}
