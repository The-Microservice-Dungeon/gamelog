package com.github.tmd.gamelog.adapter.event.kafka;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.after;
import static org.mockito.Mockito.verify;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatus;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.LobbyAction;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatus;
import com.github.tmd.gamelog.application.history.GameHistoryService;
import com.github.tmd.gamelog.application.history.RobotHistoryService;
import com.github.tmd.gamelog.application.history.TradingHistoryService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("integration")
@SpringBootTest(properties = { "spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer" })
@EnabledIfEnvironmentVariable(disabledReason = "Should run in CI", named = "CI", matches = "true")
class GameEventListenersIntegrationTest {

  @MockBean
  GameHistoryService gameHistoryService;

  @MockBean
  TradingHistoryService tradingHistoryService;

  @MockBean
  RobotHistoryService robotHistoryService;

  @Autowired
  GameEventListeners gameEventListeners;

  @Autowired
  KafkaTemplate<String, Object> template;

  @Test
  void shouldConsumeGameStatusChangedEvent() {
    // Given
    var gameId = UUID.randomUUID().toString();
    var payload = """
        {
          "status": "created",
          "gameId": "%s"
        }
        """.formatted(gameId);
    var key = UUID.randomUUID().toString();

    var producerRecord = KafkaProducerRecordBuilder.<String, Object>builder()
        .topic("status")
        .payload(payload)
        .key(key)
        .eventId(gameId)
        .transactionId(gameId)
        .type("status")
        .build().toProducerRecord();

    // When
    template.send(producerRecord);

    // Then
    verify(gameHistoryService, after(5000).only()).insertGameStatusHistory(eq(UUID.fromString(gameId)), eq(GameStatus.CREATED), any());
  }

  @Test
  void shouldConsumePlayerStatusChangedEvent() {
    // Given
    var gameId = UUID.randomUUID();
    var userId = UUID.randomUUID();
    var eventId = gameId;
    var userName = "player1";
    var payload = """
        {
          "userId": "%s",
          "lobbyAction": "joined",
          "userName": "%s"
        }
        """.formatted(userId.toString(), userName);
    var key = UUID.randomUUID().toString();

    var producerRecord = KafkaProducerRecordBuilder.<String, Object>builder()
        .topic("playerStatus")
        .payload(payload)
        .key(key)
        .eventId(eventId.toString())
        .transactionId(gameId.toString())
        .type("playerStatus")
        .build().toProducerRecord();

    // When
    template.send(producerRecord);

    // Then
    verify(gameHistoryService, after(5000).only()).insertGamePlayerStatusHistory(eq(gameId), eq(userId), eq(userName), eq(
        LobbyAction.JOINED), any());
  }

  @Test
  void shouldConsumeRoundStatusChangedEvent() {
    // Given
    var gameId = UUID.randomUUID();
    var roundNumber = 1;
    var roundId = UUID.fromString("00000000-0000-0000-0000-000000000000");
    var eventId = gameId;
    var payload = """
        {
          "roundNumber": %d,
          "roundStatus": "started"
        }
        """.formatted(roundNumber);
    var key = UUID.randomUUID().toString();

    var producerRecord = KafkaProducerRecordBuilder.<String, Object>builder()
        .topic("roundStatus")
        .payload(payload)
        .key(key)
        .eventId(eventId.toString())
        .transactionId(gameId.toString())
        .type("roundStatus")
        .build().toProducerRecord();

    // When
    template.send(producerRecord);

    // Then
    verify(gameHistoryService, after(5000).only()).insertGameRoundStatusHistory(eq(gameId), eq(roundId), eq(roundNumber), eq(
        RoundStatus.STARTED), any());
  }
}