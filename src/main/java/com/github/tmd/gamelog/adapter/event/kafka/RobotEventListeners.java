package com.github.tmd.gamelog.adapter.event.kafka;

import com.github.tmd.gamelog.adapter.event.gameEvent.robot.FightingEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.MiningEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.MovementEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.PlanetBlockedEvent;
import com.github.tmd.gamelog.application.history.RobotHistoryService;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
public class RobotEventListeners {

  private final RobotHistoryService robotHistoryService;

  public RobotEventListeners(
      RobotHistoryService robotHistoryService) {
    this.robotHistoryService = robotHistoryService;
  }

  @RetryableTopic(attempts = "3", backoff = @Backoff)
  @KafkaListener(topics = "movement")
  public void movementEvent(@Payload MovementEvent event, @Header(name = "transactionId") UUID transactionId, @Header(name = "timestamp") String timestampHeader, MessageHeaders headers) {
    if(event.success()) {
      // TODO: Point-relevant
      var timestamp = ZonedDateTime.parse(timestampHeader).toInstant();
      robotHistoryService.insertMovementHistory(transactionId, event.robots(), event.planet()
          .planetId(), event.planet().movementDifficulty(), timestamp);
    }
  }

  @RetryableTopic(attempts = "3", backoff = @Backoff)
  @KafkaListener(topics = "planet-blocked")
  public void planetBlockedEvent(@Payload PlanetBlockedEvent event, @Header(name = "transactionId") UUID transactionId, @Header(name = "timestamp") String timestampHeader, MessageHeaders headers) {
    if(event.success()) {
      // TODO: Point-relevant
      var timestamp = ZonedDateTime.parse(timestampHeader).toInstant();
      robotHistoryService.insertPlanetBlockHistory(transactionId, event.planetId(), timestamp);
    }
  }

  @RetryableTopic(attempts = "3", backoff = @Backoff)
  @KafkaListener(topics = "mining")
  public void miningEvent(@Payload MiningEvent event, @Header(name = "transactionId") UUID transactionId, @Header(name = "timestamp") String timestampHeader, MessageHeaders headers) {
    if(event.success()) {
      // TODO: Point-relevant
      var timestamp = ZonedDateTime.parse(timestampHeader).toInstant();
      robotHistoryService.insertMiningHistory(transactionId, event.updateInventory(), event.resourceType(), timestamp);
    }
  }

  @RetryableTopic(attempts = "3", backoff = @Backoff)
  @KafkaListener(topics = "fighting")
  public void fightingEvent(@Payload FightingEvent event, @Header(name = "transactionId") UUID transactionId, @Header(name = "timestamp") String timestampHeader, MessageHeaders headers) {
    if(event.success()) {
      // TODO: Point-relevant
      var timestamp = ZonedDateTime.parse(timestampHeader).toInstant();
      robotHistoryService.insertFightHistory(transactionId, event.attacker(), event.defender(),
          event.remainingDefenderHealth(), timestamp);
    }
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
