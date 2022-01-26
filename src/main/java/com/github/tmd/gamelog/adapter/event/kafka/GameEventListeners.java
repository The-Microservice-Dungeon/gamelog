package com.github.tmd.gamelog.adapter.event.kafka;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatusEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.PlayerStatusChangedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatusChangedEvent;
import com.github.tmd.gamelog.application.GameLifecycleHook;
import com.github.tmd.gamelog.application.GameService;
import com.github.tmd.gamelog.domain.Game;
import java.nio.ByteBuffer;
import java.time.ZonedDateTime;
import java.util.List;
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
  private final List<GameLifecycleHook> lifecycleHooks;
  private final GameService gameService;

  @Autowired
  public GameEventListeners(
      List<GameLifecycleHook> lifecycleHooks,
      GameService gameService) {
    this.lifecycleHooks = lifecycleHooks;
    this.gameService = gameService;
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
    lifecycleHooks.forEach(hook -> hook.onGameStatus(event, timestamp));
  }

  @RetryableTopic(attempts = "3", backoff = @Backoff)
  @KafkaListener(topics = "playerStatus")
  public void playerStatusChangedEvent(@Payload PlayerStatusChangedEvent event,
      @Header(name = KafkaDungeonHeader.KEY_TIMESTAMP) String timestampHeader) {
    var timestamp = ZonedDateTime.parse(timestampHeader).toInstant();
    // TODO: HACK!!! Last minute Change
    //  The playerStatus Event does not include the gameId but we heavily rely on it and dont have
    //  the time to adapt a query.
    //  therefore we're just assuming that the player joined the latest game
    var gameId = this.gameService.findLatestGame()
        .map(game -> game.getId().id())
        .orElse(UUID.randomUUID()); // This is really dumb...

    lifecycleHooks.forEach(hook -> hook.onPlayerStatus(event, gameId, timestamp));
  }

  @RetryableTopic(attempts = "3", backoff = @Backoff)
  @KafkaListener(topics = "roundStatus")
  public void roundStatusChangedEvent(@Payload RoundStatusChangedEvent event,
      @Header(name = KafkaDungeonHeader.KEY_TIMESTAMP) String timestampHeader) {
    UUID roundId = event.roundId();
    var gameId = event.gameId();
    var timestamp = ZonedDateTime.parse(timestampHeader).toInstant();
    lifecycleHooks.forEach(hook -> hook.onRoundStatus(event, gameId, timestamp));
  }
}
