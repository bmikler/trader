package com.tradingplatform.tradingplatform.rate;

import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

interface RateRepository {

    Optional<BigDecimal> getPrice(CryptoCurrency currency);

    List<Rate> getAll();

}
