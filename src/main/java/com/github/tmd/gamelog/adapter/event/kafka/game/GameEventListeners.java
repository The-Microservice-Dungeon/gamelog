package com.github.tmd.gamelog.adapter.event.kafka.game;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatusEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.PlayerStatusChangedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatusChangedEvent;
import com.github.tmd.gamelog.domain.game.GameEventHandler;
import java.time.ZonedDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class GameEventListeners {
  private final GameEventHandler gameEventHandler;

  @Autowired
  public GameEventListeners(GameEventHandler gameEventHandler) {
    this.gameEventHandler = gameEventHandler;
  }

  @KafkaListener(topics = "status")
  public void gameStatusChangedEvent(@Payload GameStatusEvent event, MessageHeaders headers) {
    // TODO: Get time out of header
    switch (event.status()) {
      case STARTED -> gameEventHandler.onGameStart(event.gameId().toString(), ZonedDateTime.now());
      case CREATED -> gameEventHandler.onGameCreate(event.gameId().toString(), ZonedDateTime.now());
      case ENDED -> gameEventHandler.onGameEnd(event.gameId().toString(), ZonedDateTime.now());
    }
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
