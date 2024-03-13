package com.trader.tradingplatform.trade;


import com.trader.tradingplatform.shared.CryptoCurrency;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
class TradeService {

    private final AccountRepository accountRepository;
    private final TradeOfferRepository tradeOfferRepository;
    private final TradeOfferFactory tradeOfferFactory;
    private final ApplicationEventPublisher eventPublisher;

    TradeOffer createOffer(TradeOfferCommand command) {
        TradeOffer tradeOffer = tradeOfferFactory.createOffer(command.userId(), command.currency(), command.amount());
        tradeOfferRepository.save(tradeOffer);
        log.info("Trade offer for user with id {} has been created, {} {} for {} $", command.userId(), tradeOffer.getAmount(), tradeOffer.getCurrency(), tradeOffer.getRate());
        return tradeOffer;
    }

    void removeOutdatedOffers() {
        //TODO
    }

    @Transactional
    void buy(TradeCommand command) {
        Account account = getAccountByUserIdOrThrow(command.userId());
        TradeOffer tradeOffer = getTradeOfferOrThrow(command.tradeOfferId(), command.userId());
        account.buy(tradeOffer.getCurrency(), tradeOffer.getAmount(), tradeOffer.getRate());
        accountRepository.save(account);
        tradeOfferRepository.delete(tradeOffer);
        log.info("User with id {} has bought {} {} for {} $", command.userId(), tradeOffer.getAmount(), tradeOffer.getCurrency(), tradeOffer.getRate());
        eventPublisher.publishEvent(new TradeEvent(this, command.userId(), tradeOffer.getAmount(), tradeOffer.getRate(), tradeOffer.getCurrency()));
    }

    @Transactional
    void sell(TradeCommand command) {
        Account account = getAccountByUserIdOrThrow(command.userId());
        TradeOffer tradeOffer = getTradeOfferOrThrow(command.tradeOfferId(), command.userId());
        account.sell(tradeOffer.getCurrency(), tradeOffer.getAmount(), tradeOffer.getRate());
        accountRepository.save(account);
        tradeOfferRepository.delete(tradeOffer);
        log.info("User with id {} has sold {} {} for {} $", command.userId(), tradeOffer.getAmount(), tradeOffer.getCurrency(), tradeOffer.getRate());
        eventPublisher.publishEvent(new TradeEvent(this, command.userId(), tradeOffer.getAmount().multiply(BigDecimal.valueOf(-1)), tradeOffer.getRate(), tradeOffer.getCurrency()));
    }

    private Account getAccountByUserIdOrThrow(UUID userId) {
        return accountRepository.getAccountByUserId(userId).orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }

    private TradeOffer getTradeOfferOrThrow(UUID tradeOfferId, UUID userId) {
        TradeOffer tradeOffer = tradeOfferRepository.findById(tradeOfferId).orElseThrow(() -> new EntityNotFoundException("Trade offer not found"));

        if (tradeOffer.isExpired() || !tradeOffer.isForUser(userId)) {
            log.error("Invalid trade offer, user with id {} tried to access trade offer with id {}", userId, tradeOfferId);
            throw new IllegalArgumentException("Invalid trade offer");
        }

        return tradeOffer;
    }
}

record TradeOfferCommand(UUID userId, CryptoCurrency currency, BigDecimal amount) {};
record TradeCommand(UUID userId, UUID tradeOfferId) {};