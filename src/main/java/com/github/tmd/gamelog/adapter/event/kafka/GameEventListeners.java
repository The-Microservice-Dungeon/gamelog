package com.github.tmd.gamelog.adapter.event.kafka;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatusEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.PlayerStatusChangedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatusChangedEvent;
import com.github.tmd.gamelog.application.history.GameHistoryService;
import com.github.tmd.gamelog.application.history.RobotHistoryService;
import com.github.tmd.gamelog.application.history.TradingHistoryService;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
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

  @RetryableTopic(attempts = "3", backoff = @Backoff)
  @KafkaListener(topics = "status", properties = {
      "spring.json.value.default.type=com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatusEvent"
  })
  public void gameStatusChangedEvent(@Payload GameStatusEvent event, @Header(name = "timestamp") String timestampHeader) {
    var timestamp = ZonedDateTime.parse(timestampHeader).toInstant();
    gameHistoryService.insertGameStatusHistory(event.gameId(), event.status(), timestamp);
  }

  @RetryableTopic(attempts = "3", backoff = @Backoff)
  @KafkaListener(topics = "playerStatus", properties = {
      "spring.json.value.default.type=com.github.tmd.gamelog.adapter.event.gameEvent.game.PlayerStatusChangedEvent"
  })
  public void playerStatusChangedEvent(@Payload PlayerStatusChangedEvent event,
      @Header(name = "timestamp") String timestampHeader, @Header(name = "transactionId") UUID gameId) {
    var timestamp = ZonedDateTime.parse(timestampHeader).toInstant();
    gameHistoryService.insertGamePlayerStatusHistory(gameId, event.userId(), event.userName(), event.lobbyAction(), timestamp);
  }

  @RetryableTopic(attempts = "3", backoff = @Backoff)
  @KafkaListener(topics = "roundStatus", properties = {
      "spring.json.value.default.type=com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatusChangedEvent"
  })
  public void roundStatusChangedEvent(@Payload RoundStatusChangedEvent event,
      @Header(name = "timestamp") String timestampHeader, @Header(name = "transactionId") UUID gameId) {

    // TODO: Round ID not present
    UUID roundId = UUID.fromString("00000000-0000-0000-0000-000000000000");

    var timestamp = ZonedDateTime.parse(timestampHeader).toInstant();
    gameHistoryService.insertGameRoundStatusHistory(gameId, roundId, event.roundNumber(), event.roundStatus(), timestamp);

    switch (event.roundStatus()) {
      case ENDED -> {
        // TODO: Well this could take a loooooong time
        // TODO: Multiple synchronous calls
        for(var player : gameHistoryService.getAllParticipatingPlayersInGame(gameId)) {
          this.robotHistoryService.insertRobotRoundHistoryForPlayer(roundId, player);
        }

        this.tradingHistoryService.insertBalanceHistory(roundId);
      }
    }
  }
}
