package com.tradingplatform.tradingplatform.rate;


import com.tradingplatform.tradingplatform.shared.CryptoCurrency;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

interface RateRepository {

    void updateRates(List<Rate> rates);
    Optional<BigDecimal> getPrice(CryptoCurrency currency);
    List<Rate> getAll();

}
