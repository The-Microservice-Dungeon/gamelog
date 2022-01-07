package com.github.tmd.gamelog.adapter.event.kafka;

import com.github.tmd.gamelog.adapter.event.gameEvent.trading.BankCreatedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.CurrentItemPriceEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.CurrentResourcePriceEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.TradingEvent;
import com.github.tmd.gamelog.application.history.TradingHistoryService;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.ZonedDateTime;
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

@Component
@Slf4j
public class TradingEventListeners {
  private final TradingHistoryService tradingHistoryService;

  public TradingEventListeners(
      TradingHistoryService tradingHistoryService) {
    this.tradingHistoryService = tradingHistoryService;
  }

  @DltHandler
  void dltHandler(Message<?> msg,
      @Header(KafkaHeaders.ORIGINAL_OFFSET) byte[] offset,
      @Header(KafkaHeaders.EXCEPTION_FQCN) String descException,
      @Header(KafkaHeaders.EXCEPTION_STACKTRACE) String stacktrace,
      @Header(KafkaHeaders.EXCEPTION_MESSAGE) String errorMessage) {
    log.error("""
        =============== GAME DLT ===============
        Message: {}
        Original Offset: {}
        Desc Exception: {}
        Error Message: {}
        Stacktrace: {}
        """, msg, ByteBuffer.wrap(offset).getLong(), descException, errorMessage, stacktrace);
  }

  @RetryableTopic(attempts = "3", backoff = @Backoff)
  @KafkaListener(topics = "trades")
  public void tradingEvent(@Payload TradingEvent event, @Header(name = "timestamp") String timestampHeader, @Header(name = "transactionId") UUID transactionId, MessageHeaders headers) {
    if(event.success()) {
      // TODO: Point-relevant
      var timestamp = ZonedDateTime.parse(timestampHeader).toInstant();
      this.tradingHistoryService.insertTradingHistory(transactionId, event.amount(), timestamp);
    }
  }

  // region Irrelevant Kafka Listeners for scores
  /*@KafkaListener(topics = "bank-created")
  public void bankCreatedEvent(@Payload BankCreatedEvent event, MessageHeaders headers) {

  }

  @KafkaListener(topics = "current-item-prices")
  public void currentItemPricesChangedEvent(@Payload CurrentItemPriceEvent event,
      MessageHeaders headers) {

  }

  @KafkaListener(topics = "current-resource-prices")
  public void currentResourcePricesChangedEvent(@Payload CurrentResourcePriceEvent event,
      MessageHeaders headers) {

  }*/

  // endregion
}
