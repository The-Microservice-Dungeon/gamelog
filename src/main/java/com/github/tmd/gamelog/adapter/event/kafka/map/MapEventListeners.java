package com.github.tmd.gamelog.adapter.event.kafka.map;

import com.github.tmd.gamelog.adapter.event.gameEvent.map.GameworldCreatedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.map.PlanetExploredEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.map.ResourceMinedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.map.ResourceReplenishedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.map.SpacestationCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MapEventListeners {

  @KafkaListener(topics = "gameworld-created")
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

  }


}
