package com.github.tmd.gamelog.adapter.event.kafka.game;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatusEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.PlayerStatusChangedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatusChangedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class GameEventListeners {

  @KafkaListener(topics = "status")
  public void gameStatusChangedEvent(@Payload GameStatusEvent event, MessageHeaders headers) {

  }

  @KafkaListener(topics = "playerStatus")
  public void playerStatusChangedEvent(@Payload PlayerStatusChangedEvent event,
      MessageHeaders headers) {

  }

  @KafkaListener(topics = "roundStatus")
  public void roundStatusChangedEvent(@Payload RoundStatusChangedEvent event,
      MessageHeaders headers) {

  }
}
