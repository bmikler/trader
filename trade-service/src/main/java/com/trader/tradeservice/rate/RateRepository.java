package com.trader.tradeservice.rate;


import com.trader.tradeservice.shared.CryptoCurrency;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

interface RateRepository {

    void updateRates(List<Rate> rates);
    Optional<BigDecimal> getPrice(CryptoCurrency currency);
    List<Rate> getAll();

}
