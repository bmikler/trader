package com.trader.tradingplatform.tradehistory;

import com.trader.tradingplatform.shared.CryptoCurrency;
import com.trader.tradingplatform.user.SecurityUser;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/trade-history")
@RequiredArgsConstructor
class TradeHistoryController {

    private final TradeHistoryService tradeHistoryService;

    @GetMapping
    ResponseEntity<List<HistoryDto>> getTradeHistory(@AuthenticationPrincipal SecurityUser user, @RequestParam LocalDate start, @RequestParam LocalDate end) {
        List<HistoryDto> history = tradeHistoryService.getTradeHistory(new HistoryQuery(user.getId(), start, end));
        return ResponseEntity.ok().body(history);
    }
}

record HistoryRequest(@Past LocalDateTime start, @PastOrPresent LocalDateTime end) {}

record HistoryDto(LocalDateTime timestamp, BigDecimal amount, BigDecimal rate, CryptoCurrency currency) {}
