package com.github.tmd.gamelog.adapter.event.kafka.trading;

import com.github.tmd.gamelog.adapter.event.gameEvent.trading.BankCreatedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.CurrentItemPriceEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.CurrentResourcePriceEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.TradingEvent;
import com.github.tmd.gamelog.application.service.TradingHistoryService;
import java.util.UUID;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class TradingEventListeners {
  private final TradingHistoryService tradingHistoryService;

  public TradingEventListeners(
      TradingHistoryService tradingHistoryService) {
    this.tradingHistoryService = tradingHistoryService;
  }

  @KafkaListener(topics = "trades")
  public void tradingEvent(@Payload TradingEvent event, @Header(name = "transactionId") UUID transactionId, MessageHeaders headers) {
    if(event.success()) {
      // TODO: Point-relevant
      this.tradingHistoryService.insertTradingHistory(transactionId, event.amount());
    }
  }

  // region Irrelevant Kafka Listeners for scores
  @KafkaListener(topics = "bank-created")
  public void bankCreatedEvent(@Payload BankCreatedEvent event, MessageHeaders headers) {

  }

  @KafkaListener(topics = "current-item-prices")
  public void currentItemPricesChangedEvent(@Payload CurrentItemPriceEvent event,
      MessageHeaders headers) {

  }

  @KafkaListener(topics = "current-resource-prices")
  public void currentResourcePricesChangedEvent(@Payload CurrentResourcePriceEvent event,
      MessageHeaders headers) {

  }

  // endregion
}
