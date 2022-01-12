package com.github.tmd.gamelog.adapter.event.kafka;

import com.github.tmd.gamelog.adapter.event.gameEvent.map.GameworldCreatedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.map.PlanetExploredEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.map.ResourceMinedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.map.ResourceReplenishedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.map.SpacestationCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Kafka listeners for Map Service, defined in:
 * <a href="https://the-microservice-dungeon.github.io/docs/asyncapi/map">Map AsyncAPI</a>
 */
@Component
public class MapEventListeners {

  // region Irrelevant Kafka Listeners for scores
  /*@KafkaListener(topics = "gameworld-created")
  public void gameworldCreatedEvent(@Payload GameworldCreatedEvent event, MessageHeaders headers) {

  }

  @KafkaListener(topics = "spacestation-created")
  public void spacestationCreatedEvent(@Payload SpacestationCreatedEvent event,
      MessageHeaders headers) {

  }

  @KafkaListener(topics = "planet-explored")
  public void planetExploredEvent(@Payload PlanetExploredEvent event, MessageHeaders headers) {

  }

  @KafkaListener(topics = "resource-mined")
  public void resourceMinedEvent(@Payload ResourceMinedEvent event, MessageHeaders headers) {

  }

  @KafkaListener(topics = "resource-replenished")
  public void resourceReplenishedCreatedEvent(@Payload ResourceReplenishedEvent event,
      MessageHeaders headers) {

  }*/

  //endregion

}
