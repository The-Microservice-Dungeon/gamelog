package com.github.tmd.gamelog.adapter.event.kafka;

//import com.github.tmd.gamelog.adapter.event.gameEvent.trading.CurrentItemPriceEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.CurrentResourcePriceEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.TradingEvent;
import com.github.tmd.gamelog.adapter.metrics.MetricService;
import com.github.tmd.gamelog.application.GameLifecycleHook;
import java.nio.ByteBuffer;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
  private final List<GameLifecycleHook> lifecycleHooks;

  @Autowired
  public TradingEventListeners(
      List<GameLifecycleHook> lifecycleHooks) {
    this.lifecycleHooks = lifecycleHooks;
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
    var timestamp = ZonedDateTime.parse(timestampHeader).toInstant();
    lifecycleHooks.forEach(hook -> hook.onTrade(event, transactionId, timestamp));
  }
  
  /*@RetryableTopic(attempts = "3", backoff = @Backoff)
  @KafkaListener(topics = "current-item-prices")
  public void currentItemPricesChangedEvent(@Payload Set<CurrentItemPriceEvent> event,
      @Header(name = KafkaDungeonHeader.KEY_TIMESTAMP) String timestampHeader) {
    var timestamp = ZonedDateTime.parse(timestampHeader).toInstant();
    lifecycleHooks.forEach(hook -> hook.onCurrentItemPricesAnnouncement(event, timestamp));
  }
*/
  @RetryableTopic(attempts = "3", backoff = @Backoff)
  @KafkaListener(topics = "current-resource-prices")
  public void currentResourcePricesChangedEvent(@Payload Set<CurrentResourcePriceEvent> event,
      @Header(name = KafkaDungeonHeader.KEY_TIMESTAMP) String timestampHeader) {
    var timestamp = ZonedDateTime.parse(timestampHeader).toInstant();
    lifecycleHooks.forEach(hook -> hook.onCurrentResourcePricesAnnouncement(event, timestamp));
  }
}
