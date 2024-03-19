package com.trader.tradeservice.tradehistory;

import com.trader.tradeservice.security.CustomJwtAuthenticationToken;
import com.trader.tradeservice.shared.CryptoCurrency;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<List<HistoryDto>> getTradeHistory(CustomJwtAuthenticationToken auth, @RequestParam LocalDate start, @RequestParam LocalDate end) {
        List<HistoryDto> history = tradeHistoryService.getTradeHistory(new HistoryQuery(auth.getUserId(), start, end));
        return ResponseEntity.ok().body(history);
    }
}

record HistoryRequest(@Past LocalDateTime start, @PastOrPresent LocalDateTime end) {}

record HistoryDto(LocalDateTime timestamp, BigDecimal amount, BigDecimal rate, CryptoCurrency currency) {}
