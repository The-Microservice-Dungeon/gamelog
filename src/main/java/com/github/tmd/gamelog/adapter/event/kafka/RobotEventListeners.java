package com.github.tmd.gamelog.adapter.event.kafka;

import com.github.tmd.gamelog.adapter.event.gameEvent.robot.FightingEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.FightingItemUseEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.MiningEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.MovementEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.MovementItemUsedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.PlanetBlockedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.RegenerationEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.RepairItemUsedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.ResourceDistributionEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.RobotDestroyedEvent;
import com.github.tmd.gamelog.application.service.RobotHistoryService;
import java.time.Instant;
import java.util.UUID;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RobotEventListeners {

  private final RobotHistoryService robotHistoryService;

  public RobotEventListeners(
      RobotHistoryService robotHistoryService) {
    this.robotHistoryService = robotHistoryService;
  }

  @KafkaListener(topics = "movement")
  public void movementEvent(@Payload MovementEvent event, @Header(name = "transactionId") UUID transactionId, MessageHeaders headers) {
    if(event.success()) {
      // TODO: Point-relevant
      var timestamp = Instant.ofEpochMilli(headers.getTimestamp());
      robotHistoryService.insertMovementHistory(transactionId, event.robots(), event.planet()
          .planetId(), event.planet().movementDifficulty(), timestamp);
    }
  }

  @KafkaListener(topics = "planet-blocked")
  public void planetBlockedEvent(@Payload PlanetBlockedEvent event, @Header(name = "transactionId") UUID transactionId, MessageHeaders headers) {
    if(event.success()) {
      // TODO: Point-relevant
      var timestamp = Instant.ofEpochMilli(headers.getTimestamp());
      robotHistoryService.insertPlanetBlockHistory(transactionId, event.planetId(), timestamp);
    }
  }

  @KafkaListener(topics = "mining")
  public void miningEvent(@Payload MiningEvent event, @Header(name = "transactionId") UUID transactionId, MessageHeaders headers) {
    if(event.success()) {
      // TODO: Point-relevant
      var timestamp = Instant.ofEpochMilli(headers.getTimestamp());
      robotHistoryService.insertMiningHistory(transactionId, event.updateInventory(), event.resourceType(), timestamp);
    }
  }

  @KafkaListener(topics = "fighting")
  public void fightingEvent(@Payload FightingEvent event, @Header(name = "transactionId") UUID transactionId, MessageHeaders headers) {
    if(event.success()) {
      // TODO: Point-relevant
      var timestamp = Instant.ofEpochMilli(headers.getTimestamp());
      robotHistoryService.insertFightHistory(transactionId, event.attacker(), event.defender(),
          event.remainingDefenderHealth(), timestamp);
    }
  }

  // region Irrelevant Kafka Listeners for scores
  @KafkaListener(topics = "resource-distribution")
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
  public void bankDestroyedEvent(@Payload RobotDestroyedEvent event, MessageHeaders headers) {}
  // endregion
}
