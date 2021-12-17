package com.github.tmd.gamelog.adapter.event.kafka.trading;

import com.github.tmd.gamelog.adapter.event.gameEvent.map.GameworldCreatedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.BankCreatedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.CurrentItemPriceEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.CurrentResourcePriceEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.TradingEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class TradingEventListeners {

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

  @KafkaListener(topics = "trades")
  public void tradingEvent(@Payload TradingEvent event, MessageHeaders headers) {

  }
}
