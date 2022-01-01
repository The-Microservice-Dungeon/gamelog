package com.github.tmd.gamelog.adapter.event.kafka.robot;

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
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RobotEventListeners {
  @KafkaListener(topics = "movement")
  public void movementEvent(@Payload MovementEvent event, MessageHeaders headers) {
    if(event.success()) {
      // TODO: Point-relevant
    }
  }

  @KafkaListener(topics = "planet-blocked")
  public void planetBlockedEvent(@Payload PlanetBlockedEvent event, MessageHeaders headers) {
    if(event.success()) {
      // TODO: Point-relevant
    }
  }

  @KafkaListener(topics = "mining")
  public void miningEvent(@Payload MiningEvent event, MessageHeaders headers) {
    if(event.success()) {
      // TODO: Point-relevant
    }
  }

  @KafkaListener(topics = "fighting")
  public void fightingEvent(@Payload FightingEvent event, MessageHeaders headers) {
    if(event.success()) {
      // TODO: Point-relevant
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
