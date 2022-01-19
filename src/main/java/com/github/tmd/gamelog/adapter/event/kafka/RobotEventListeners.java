package com.github.tmd.gamelog.adapter.event.kafka;

import com.github.tmd.gamelog.adapter.event.gameEvent.robot.FightingEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.MiningEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.MovementEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.PlanetBlockedEvent;
import com.github.tmd.gamelog.adapter.metrics.MetricService;
import com.github.tmd.gamelog.application.GameLifecycleHook;
import java.nio.ByteBuffer;
import java.time.ZonedDateTime;
import java.util.List;
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
 * Kafka listeners for Robot Service, defined in:
 * <a href="https://the-microservice-dungeon.github.io/docs/asyncapi/robot">Robot AsyncAPI</a>
 */
@Component
@Slf4j
public class RobotEventListeners {
  private final List<GameLifecycleHook> lifecycleHooks;

  @Autowired
  public RobotEventListeners(
      List<GameLifecycleHook> lifecycleHooks) {
    this.lifecycleHooks = lifecycleHooks;
  }

  @DltHandler
  void dltHandler(Message<?> msg,
      @Header(KafkaHeaders.ORIGINAL_OFFSET) byte[] offset,
      @Header(KafkaHeaders.ORIGINAL_TOPIC) String originalTopic,
      @Header(KafkaHeaders.EXCEPTION_FQCN) String descException,
      @Header(KafkaHeaders.EXCEPTION_STACKTRACE) String stacktrace,
      @Header(KafkaHeaders.EXCEPTION_MESSAGE) String errorMessage) {
    log.error("""
            =============== ROBOT DLT ===============
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
  @KafkaListener(topics = "movement")
  public void movementEvent(@Payload MovementEvent event,
      @Header(name = KafkaDungeonHeader.KEY_TRANSACTION_ID) UUID transactionId,
      @Header(name = KafkaDungeonHeader.KEY_TIMESTAMP) String timestampHeader) {
    var timestamp = ZonedDateTime.parse(timestampHeader).toInstant();

    lifecycleHooks.forEach(hook -> hook.onRobotMovement(event, transactionId, timestamp));
  }

  @RetryableTopic(attempts = "3", backoff = @Backoff)
  @KafkaListener(topics = "planet-blocked")
  public void planetBlockedEvent(@Payload PlanetBlockedEvent event,
      @Header(name = KafkaDungeonHeader.KEY_TRANSACTION_ID) UUID transactionId,
      @Header(name = KafkaDungeonHeader.KEY_TIMESTAMP) String timestampHeader) {
    var timestamp = ZonedDateTime.parse(timestampHeader).toInstant();

    lifecycleHooks.forEach(hook -> hook.onRobotPlanetBlocked(event, transactionId, timestamp));
  }

  @RetryableTopic(attempts = "3", backoff = @Backoff)
  @KafkaListener(topics = "mining")
  public void miningEvent(@Payload MiningEvent event,
      @Header(name = KafkaDungeonHeader.KEY_TRANSACTION_ID) UUID transactionId,
      @Header(name = KafkaDungeonHeader.KEY_TIMESTAMP) String timestampHeader) {
    var timestamp = ZonedDateTime.parse(timestampHeader).toInstant();

    lifecycleHooks.forEach(hook -> hook.onRobotMining(event, transactionId, timestamp));
  }

  @RetryableTopic(attempts = "3", backoff = @Backoff)
  @KafkaListener(topics = "fighting")
  public void fightingEvent(@Payload FightingEvent event,
      @Header(name = KafkaDungeonHeader.KEY_TRANSACTION_ID) UUID transactionId,
      @Header(name = KafkaDungeonHeader.KEY_TIMESTAMP) String timestampHeader) {
    var timestamp = ZonedDateTime.parse(timestampHeader).toInstant();

    lifecycleHooks.forEach(hook -> hook.onRobotFighting(event, transactionId, timestamp));
  }

  // region Irrelevant Kafka Listeners for scores
  /*@KafkaListener(topics = "resource-distribution")
  public void resourceDistributionEvent(@Payload ResourceDistributionEvent event, MessageHeaders headers) {}

  @KafkaListener(topics = "regeneration")
  public void regenerationEvent(@Payload RegenerationEvent event, MessageHeaders headers) {}

  @KafkaListener(topics = "item-fighting")
  public void fightingItemEvent(@Payload FightingItemUseEvent event, MessageHeaders headers) {}

  @KafkaListener(topics = "item-repair")
  public void repairItemEvent(@Payload RepairItemUsedEvent event, MessageHeaders headers) {}

  @KafkaListener(topics = "item-movement")
  public void movementItemEvent(@Payload MovementItemUsedEvent event, MessageHeaders headers) {}

  @KafkaListener(topics = "robot-destroyed")
  public void bankDestroyedEvent(@Payload RobotDestroyedEvent event, MessageHeaders headers) {}*/
  // endregion
}
