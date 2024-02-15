package com.tradingplatform.tradingplatform.trade;


import com.tradingplatform.tradingplatform.rate.CryptoCurrency;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class TradeService {

    private final AccountRepository accountRepository;
    private final TradeOfferRepository tradeOfferRepository;
    private final TradeOfferFactory tradeOfferFactory;


    TradeOffer createOffer(TradeOfferCommand command) {
        TradeOffer tradeOffer = tradeOfferFactory.createOffer(command.userId(), command.currency(), command.amount());
        tradeOfferRepository.save(tradeOffer);
        return tradeOffer;
    }

    void buy(TradeCommand command) {
        Account account = getAccountByUserIdOrThrow(command.userId());
        TradeOffer tradeOffer = getTradeOfferOrThrow(command.tradeOfferId(), command.userId());
        account.buy(tradeOffer.getCurrency(), tradeOffer.getAmount(), tradeOffer.getRate());
        accountRepository.save(account);
        tradeOfferRepository.delete(tradeOffer);
    }

    void sell(TradeCommand command) {
        Account account = getAccountByUserIdOrThrow(command.userId());
        TradeOffer tradeOffer = getTradeOfferOrThrow(command.tradeOfferId(), command.userId());
        account.sell(tradeOffer.getCurrency(), tradeOffer.getAmount(), tradeOffer.getRate());
        accountRepository.save(account);
        tradeOfferRepository.delete(tradeOffer);
    }

    private Account getAccountByUserIdOrThrow(UUID userId) {
        return accountRepository.getAccountByUserId(userId).orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }

    private TradeOffer getTradeOfferOrThrow(UUID tradeOfferId, UUID userId) {
        TradeOffer tradeOffer = tradeOfferRepository.findById(tradeOfferId).orElseThrow(() -> new EntityNotFoundException("Trade offer not found"));

        if (tradeOffer.isExpired() || !tradeOffer.isForUser(userId)) {
            throw new IllegalArgumentException("Invalid trade offer");
        }

        return tradeOffer;
    }
}

record TradeOfferCommand(UUID userId, CryptoCurrency currency, BigDecimal amount) {};
record TradeCommand(UUID userId, UUID tradeOfferId) {};