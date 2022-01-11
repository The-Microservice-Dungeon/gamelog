package com.github.tmd.gamelog.adapter.event.kafka;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatusEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.PlayerStatusChangedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatusChangedEvent;
import com.github.tmd.gamelog.application.history.GameHistoryService;
import com.github.tmd.gamelog.application.history.RobotHistoryService;
import com.github.tmd.gamelog.application.history.TradingHistoryService;
import java.nio.ByteBuffer;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GameEventListeners {
  private final GameHistoryService gameHistoryService;
  private final RobotHistoryService robotHistoryService;
  private final TradingHistoryService tradingHistoryService;

  @Autowired
  public GameEventListeners(
      GameHistoryService gameHistoryService,
      RobotHistoryService robotHistoryService,
      TradingHistoryService tradingHistoryService) {
    this.gameHistoryService = gameHistoryService;
    this.robotHistoryService = robotHistoryService;
    this.tradingHistoryService = tradingHistoryService;
  }

  @DltHandler
  void dltHandler(Message<?> msg,
      @Header(KafkaHeaders.ORIGINAL_OFFSET) byte[] offset,
      @Header(KafkaHeaders.EXCEPTION_FQCN) String descException,
      @Header(KafkaHeaders.ORIGINAL_TOPIC) String originalTopic,
      @Header(KafkaHeaders.EXCEPTION_STACKTRACE) String stacktrace,
      @Header(KafkaHeaders.EXCEPTION_MESSAGE) String errorMessage) {
    log.error("""
        =============== GAME DLT ===============
        Message: {}
        Original Topic: {}
        Original Offset: {}
        Desc Exception: {}
        Error Message: {}
        Stacktrace: {}
        """, msg, originalTopic, ByteBuffer.wrap(offset).getLong(), descException, errorMessage, stacktrace);
  }

  @RetryableTopic(attempts = "3", backoff = @Backoff)
  @KafkaListener(topics = "status")
  public void gameStatusChangedEvent(@Payload GameStatusEvent event, @Header(name = "timestamp") String timestampHeader) {
    var timestamp = ZonedDateTime.parse(timestampHeader).toInstant();
    gameHistoryService.insertGameStatusHistory(event.gameId(), event.status(), timestamp);
    throw new RuntimeException();
  }

  @RetryableTopic(attempts = "3", backoff = @Backoff)
  @KafkaListener(topics = "playerStatus")
  public void playerStatusChangedEvent(@Payload PlayerStatusChangedEvent event,
      @Header(name = "timestamp") String timestampHeader, @Header(name = "transactionId") UUID gameId) {
    var timestamp = ZonedDateTime.parse(timestampHeader).toInstant();
    gameHistoryService.insertGamePlayerStatusHistory(gameId, event.userId(), event.userName(), event.lobbyAction(), timestamp);
  }

  @RetryableTopic(attempts = "3", backoff = @Backoff)
  @KafkaListener(topics = "roundStatus")
  public void roundStatusChangedEvent(@Payload RoundStatusChangedEvent event,
      @Header(name = "timestamp") String timestampHeader, @Header(name = "transactionId") UUID gameId) {
    UUID roundId = event.roundId();

    var timestamp = ZonedDateTime.parse(timestampHeader).toInstant();
    gameHistoryService.insertGameRoundStatusHistory(gameId, roundId, event.roundNumber(), event.roundStatus(), timestamp);

    switch (event.roundStatus()) {
      case ENDED -> {
        // TODO: Well this could take a loooooong time
        // TODO: Multiple synchronous calls
        // TODO: Schedule the calls so that they could be called at a later point
        for(var player : gameHistoryService.getAllParticipatingPlayersInGame(gameId)) {
          this.robotHistoryService.insertRobotRoundHistoryForPlayer(roundId, player);
        }

        this.tradingHistoryService.insertBalanceHistory(roundId, event.roundNumber());
      }
    }
  }
}
