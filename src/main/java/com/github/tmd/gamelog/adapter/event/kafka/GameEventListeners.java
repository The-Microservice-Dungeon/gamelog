package com.github.tmd.gamelog.adapter.event.kafka;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatusEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.PlayerStatusChangedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatusChangedEvent;
import com.github.tmd.gamelog.application.service.GameHistoryService;
import com.github.tmd.gamelog.application.service.RobotHistoryService;
import com.github.tmd.gamelog.application.service.TradingHistoryService;
import com.github.tmd.gamelog.domain.player.PlayerEventHandler;
import com.github.tmd.gamelog.application.handler.GameEventHandler;
import java.time.Instant;
import java.time.ZonedDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class GameEventListeners {
  private final PlayerEventHandler playerEventHandler;
  private final GameEventHandler gameEventHandler;
  private final GameHistoryService gameHistoryService;
  private final RobotHistoryService robotHistoryService;
  private final TradingHistoryService tradingHistoryService;

  @Autowired
  public GameEventListeners(PlayerEventHandler playerEventHandler,
      GameEventHandler scoreboardEventHandler,
      GameHistoryService gameHistoryService,
      RobotHistoryService robotHistoryService,
      TradingHistoryService tradingHistoryService) {
    this.playerEventHandler = playerEventHandler;
    this.gameEventHandler = scoreboardEventHandler;
    this.gameHistoryService = gameHistoryService;
    this.robotHistoryService = robotHistoryService;
    this.tradingHistoryService = tradingHistoryService;
  }
  
  @KafkaListener(topics = "status")
  public void gameStatusChangedEvent(@Payload GameStatusEvent event, MessageHeaders headers) {

    var timestamp = Instant.ofEpochMilli(headers.getTimestamp());
    gameHistoryService.insertGameStatusHistory(event.gameId(), event.status(), timestamp);

    switch (event.status()) {
      case STARTED -> gameEventHandler.onCreateGame(event.gameId());
      case ENDED -> gameEventHandler.onEndGame(event.gameId());
    }
  }

  @KafkaListener(topics = "playerStatus")
  public void playerStatusChangedEvent(@Payload PlayerStatusChangedEvent event,
      MessageHeaders headers) {

    var timestamp = Instant.ofEpochMilli(headers.getTimestamp());
    gameHistoryService.insertGamePlayerStatusHistory(event.gameId(), event.userId(), event.userName(), event.lobbyAction(), timestamp);

    switch (event.lobbyAction()) {
      case JOINED -> playerEventHandler.onPlayerRegister(event.userId(), event.userName(), ZonedDateTime.now());
    }
  }

  @KafkaListener(topics = "roundStatus")
  public void roundStatusChangedEvent(@Payload RoundStatusChangedEvent event,
      MessageHeaders headers) {

    var timestamp = Instant.ofEpochMilli(headers.getTimestamp());
    gameHistoryService.insertGameRoundStatusHistory(event.gameId(), event.roundId(), event.roundNumber(), event.roundStatus(), timestamp);

    switch (event.roundStatus()) {
      case STARTED -> gameEventHandler.onStartRound(event.gameId(), event.roundId(), event.roundNumber());
      case ENDED -> {
        // TODO: Well this could take a loooooong time
        // TODO: Multiple synchronous calls
        for(var player : gameHistoryService.getAllParticipatingPlayersInGame(event.gameId())) {
          this.robotHistoryService.insertRobotRoundHistoryForPlayer(event.roundId(), player);
        }

        this.tradingHistoryService.insertBalanceHistory(event.roundId());

        gameEventHandler.onEndRound(event.gameId(), event.roundId(), event.roundNumber());
      }
    }
  }
}
