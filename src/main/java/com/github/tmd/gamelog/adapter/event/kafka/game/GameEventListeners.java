package com.github.tmd.gamelog.adapter.event.kafka.game;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatusEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.PlayerStatusChangedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatusChangedEvent;
import com.github.tmd.gamelog.domain.player.PlayerEventHandler;
import com.github.tmd.gamelog.domain.scoreboard.event.ScoreboardEventHandler;
import java.time.ZonedDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class GameEventListeners {
  private final PlayerEventHandler playerEventHandler;
  private final ScoreboardEventHandler scoreboardEventHandler;

  @Autowired
  public GameEventListeners(PlayerEventHandler playerEventHandler,
      ScoreboardEventHandler scoreboardEventHandler) {
    this.playerEventHandler = playerEventHandler;
    this.scoreboardEventHandler = scoreboardEventHandler;
  }

  @KafkaListener(topics = "status")
  public void gameStatusChangedEvent(@Payload GameStatusEvent event, MessageHeaders headers) {
    switch (event.status()) {
      case STARTED -> scoreboardEventHandler.onInitializeScoreboard(event.gameId());
    }
  }

  @KafkaListener(topics = "playerStatus")
  public void playerStatusChangedEvent(@Payload PlayerStatusChangedEvent event,
      MessageHeaders headers) {
    switch (event.lobbyAction()) {
      case JOINED -> playerEventHandler.onPlayerRegister(event.userId(), event.userName(), ZonedDateTime.now());
    }
  }

  @KafkaListener(topics = "roundStatus")
  public void roundStatusChangedEvent(@Payload RoundStatusChangedEvent event,
      MessageHeaders headers) {

  }
}
