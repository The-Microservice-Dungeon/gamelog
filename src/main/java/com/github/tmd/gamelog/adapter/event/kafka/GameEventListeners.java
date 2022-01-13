package com.github.tmd.gamelog.adapter.event.kafka;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatusEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.PlayerStatusChangedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatusChangedEvent;
import com.github.tmd.gamelog.application.history.GameHistoryService;
import com.github.tmd.gamelog.application.history.RobotHistoryService;
import com.github.tmd.gamelog.application.history.TradingHistoryService;
import com.github.tmd.gamelog.application.score.service.RoundScoreService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import java.nio.ByteBuffer;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

/**
 * Kafka listeners for Game Service, defined in:
 * <a href="https://the-microservice-dungeon.github.io/docs/asyncapi/game">Game AsyncAPI</a>
 */
@Component
@Slf4j
public class GameEventListeners {

  private final GameHistoryService gameHistoryService;
  private final RobotHistoryService robotHistoryService;
  private final TradingHistoryService tradingHistoryService;
  private final RoundScoreService roundScoreService;

  // TODO: We're exposing and setting metrics here and are therefore violating the SRP
  private final MeterRegistry meterRegistry;

  @Autowired
  public GameEventListeners(
      GameHistoryService gameHistoryService,
      RobotHistoryService robotHistoryService,
      TradingHistoryService tradingHistoryService,
      RoundScoreService roundScoreService,
      MeterRegistry meterRegistry) {
    this.gameHistoryService = gameHistoryService;
    this.robotHistoryService = robotHistoryService;
    this.tradingHistoryService = tradingHistoryService;
    this.roundScoreService = roundScoreService;
    this.meterRegistry = meterRegistry;
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
            """, msg, originalTopic, ByteBuffer.wrap(offset).getLong(), descException, errorMessage,
        stacktrace);
  }

  @RetryableTopic(attempts = "3", backoff = @Backoff)
  @KafkaListener(topics = "status")
  public void gameStatusChangedEvent(@Payload GameStatusEvent event,
      @Header(name = KafkaDungeonHeader.KEY_TIMESTAMP) String timestampHeader) {
    var timestamp = ZonedDateTime.parse(timestampHeader).toInstant();
    gameHistoryService.insertGameStatusHistory(event.gameId(), event.status(), timestamp);

    // We're setting the round status as a plain string label, while maintaining a constant gauge
    // this is a hack to be able to query round status. Also used in the other round status
    meterRegistry.gauge("tmd_game_status", Set.of(Tag.of("game_id", event.gameId().toString()),
        Tag.of("status", event.status().toString())), 1);
  }

  @RetryableTopic(attempts = "3", backoff = @Backoff)
  @KafkaListener(topics = "playerStatus")
  public void playerStatusChangedEvent(@Payload PlayerStatusChangedEvent event,
      @Header(name = KafkaDungeonHeader.KEY_TIMESTAMP) String timestampHeader,
      @Header(name = KafkaDungeonHeader.KEY_TRANSACTION_ID) UUID gameId) {
    var timestamp = ZonedDateTime.parse(timestampHeader).toInstant();
    gameHistoryService.insertGamePlayerStatusHistory(gameId, event.userId(), event.userName(),
        event.lobbyAction(), timestamp);

    meterRegistry.counter("tmd_game_players", Set.of(Tag.of("game_id", gameId.toString()),
        Tag.of("status", event.lobbyAction().toString()))).increment();
  }

  @RetryableTopic(attempts = "3", backoff = @Backoff)
  @KafkaListener(topics = "roundStatus")
  public void roundStatusChangedEvent(@Payload RoundStatusChangedEvent event,
      @Header(name = KafkaDungeonHeader.KEY_TIMESTAMP) String timestampHeader,
      @Header(name = KafkaDungeonHeader.KEY_TRANSACTION_ID) UUID gameId) {

    UUID roundId = event.roundId();

    var timestamp = ZonedDateTime.parse(timestampHeader).toInstant();
    gameHistoryService.insertGameRoundStatusHistory(gameId, roundId, event.roundNumber(),
        event.roundStatus(), timestamp);

    switch (event.roundStatus()) {
      case STARTED -> {
        // Set Round Number
        meterRegistry.gauge("tmd_game_round", Set.of(Tag.of("game_id", gameId.toString())),
            event.roundNumber());

        // We're setting the round status as a plain string label, while maintaining a constant gauge
        // this is a hack to be able to query round status. Also used in the other round status
        meterRegistry.gauge("tmd_game_round_status",
            Set.of(Tag.of("game_id", gameId.toString()), Tag.of("status", "Started")), 1);
      }
      case COMMAND_INPUT_ENDED -> {
        meterRegistry.gauge("tmd_game_round_status",
            Set.of(Tag.of("game_id", gameId.toString()), Tag.of("status", "Command Input Ended")),
            1);
      }
      case ENDED -> {

        meterRegistry.gauge("tmd_game_round_status",
            Set.of(Tag.of("game_id", gameId.toString()), Tag.of("status", "Ended")), 1);

        // TODO: This part of the event handler is crucial and should be refactored
        // TODO: Well this could take a loooooong time
        // TODO: Multiple synchronous calls
        // TODO: Schedule the calls so that they could be called at a later point
        for (var player : gameHistoryService.getAllParticipatingPlayersInGame(gameId)) {
          this.robotHistoryService.insertRobotRoundHistoryForPlayer(roundId, player);
        }

        this.tradingHistoryService.insertBalanceHistory(roundId, event.roundNumber());

        // Now everything is completed, and we can calculate the roun scores
        this.roundScoreService.accumulateAndSaveRoundScoresForRound(event.roundId());
      }
    }
  }
}
