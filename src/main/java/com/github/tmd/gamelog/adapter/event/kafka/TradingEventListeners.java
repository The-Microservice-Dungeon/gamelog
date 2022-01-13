package com.github.tmd.gamelog.adapter.event.kafka;

import com.github.tmd.gamelog.adapter.event.gameEvent.trading.CurrentItemPriceEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.CurrentResourcePriceEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.TradingEvent;
import com.github.tmd.gamelog.adapter.metrics.MetricService;
import com.github.tmd.gamelog.application.history.TradingHistoryService;
import java.nio.ByteBuffer;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

/**
 * Kafka listeners for Trading Service, defined in:
 * <a href="https://the-microservice-dungeon.github.io/docs/asyncapi/trading">Trading AsyncAPI</a>
 */
@Component
@Slf4j
public class TradingEventListeners {

  private final TradingHistoryService tradingHistoryService;
  private final MetricService metricService;

  public TradingEventListeners(
      TradingHistoryService tradingHistoryService,
      MetricService metricService) {
    this.tradingHistoryService = tradingHistoryService;
    this.metricService = metricService;
  }

  @DltHandler
  void dltHandler(Message<?> msg,
      @Header(KafkaHeaders.ORIGINAL_OFFSET) byte[] offset,
      @Header(KafkaHeaders.EXCEPTION_FQCN) String descException,
      @Header(KafkaHeaders.ORIGINAL_TOPIC) String originalTopic,
      @Header(KafkaHeaders.EXCEPTION_STACKTRACE) String stacktrace,
      @Header(KafkaHeaders.EXCEPTION_MESSAGE) String errorMessage) {
    log.error("""
            =============== TRADING DLT ===============
            Message: {}
            Original Topic: {}
            Original Offset: {}
            Desc Exception: {}
            Error Message: {}
            Stacktrace: {}
            """, msg, originalTopic, ByteBuffer.wrap(offset).getLong(), descException, errorMessage,
        stacktrace);
  }

  @RetryableTopic(attempts = "3", backoff = @Backoff)
  @KafkaListener(topics = "trades")
  public void tradingEvent(@Payload TradingEvent event,
      @Header(name = KafkaDungeonHeader.KEY_TIMESTAMP) String timestampHeader,
      @Header(name = KafkaDungeonHeader.KEY_TRANSACTION_ID) UUID transactionId,
      MessageHeaders headers) {
    if (event.success()) {
      var timestamp = ZonedDateTime.parse(timestampHeader).toInstant();
      this.tradingHistoryService.insertTradingHistory(transactionId, event.amount(), timestamp);
      metricService.publishTrade();
    }
  }

  // region Irrelevant Kafka Listeners for scores
  /*@KafkaListener(topics = "bank-created")
  public void bankCreatedEvent(@Payload BankCreatedEvent event, MessageHeaders headers) {

  }*/

  @KafkaListener(topics = "current-item-prices")
  public void currentItemPricesChangedEvent(@Payload Set<CurrentItemPriceEvent> event,
      MessageHeaders headers) {
    event.stream().forEach(
        e -> metricService.publishItemPrice(e.name(), e.price())
    );
  }

  @KafkaListener(topics = "current-resource-prices")
  public void currentResourcePricesChangedEvent(@Payload Set<CurrentResourcePriceEvent> event,
      MessageHeaders headers) {
    event.stream().forEach(
        e -> metricService.publishResourcePrice(e.name(), e.price())
    );
  }

  // endregion
}
