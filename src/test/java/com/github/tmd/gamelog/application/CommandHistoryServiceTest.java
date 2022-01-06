package com.github.tmd.gamelog.application;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.*;

import com.github.tmd.gamelog.WireMockInitializer;
import com.github.tmd.gamelog.adapter.jpa.history.game.CommandHistoryJpaRepository;
import com.github.tmd.gamelog.application.history.GameHistoryService;
import com.github.tomakehurst.wiremock.WireMockServer;
import java.util.UUID;
import javax.transaction.Transactional;

import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = WireMockInitializer.class)
@Transactional
class CommandHistoryServiceTest {

  @Autowired
  WireMockServer wireMockServer;

  @Autowired
  GameHistoryService commandHistoryService;

  @Autowired
  CommandHistoryJpaRepository commandHistoryJpaRepository;

  private static final String gameIdString = "d290f1ee-6c54-4b01-90e6-d701748f0851";
  private static final String roundIdString = "ab58d128-1802-4bd2-88e1-2f9d88094c5c";
  private static final String transactionIdStringA = "8ade28ee-31c0-480c-bc84-814072545dd7";
  private static final String playerIdStringA = "e1f5e4df-d35c-4ae4-b444-bc1f940f719d";
  private static final String transactionIdStringB = "56e876dc-0ec3-48df-8d62-89044c9dbef7";
  private static final String playerIdStringB = "175d2c20-bfa2-41fd-8eac-a564850658a6";

  @Test
  void shouldSaveExecutedCommands() {
    // Given
    var gameId = UUID.fromString(gameIdString);
    var roundNumber = 1;

    addStubForCommandPath(gameId);

    // When
    this.commandHistoryService.insertExecutedCommandsHistory(gameId, roundNumber);

    // Then
    var commands = this.commandHistoryJpaRepository.findAll();
    assertThat(commands).hasSize(2);
    assertThat(commands).extracting("transactionId", "gameId", "roundId", "playerId")
      .containsExactlyInAnyOrder(
        tuple(UUID.fromString(transactionIdStringA),
          UUID.fromString(gameIdString),
          UUID.fromString(roundIdString),
          UUID.fromString(playerIdStringA)),
        tuple(UUID.fromString(transactionIdStringB),
          UUID.fromString(gameIdString),
          UUID.fromString(roundIdString),
          UUID.fromString(playerIdStringB))
      );
  }

  private void addStubForCommandPath(UUID gameId) {
    wireMockServer.stubFor(get(urlPathEqualTo("/commands"))
      .withQueryParam("gameId", equalTo(gameId.toString()))
      .withQueryParam("roundNumber", equalTo("1"))
      .willReturn(aResponse()
        .withStatus(200)
        .withHeader("Content-Type", "application/json")
        .withBody(generateBody())
        )
    );
  }

  private String generateBody() {
    String body =
      """
      {
        "gameId": "#{gameIdString}",
        "roundId": "#{roundIdString}",
        "roundNumber": 1,
        "commands": [
          {
            "transactionId": "#{transactionIdStringA}",
            "gameId": "#{gameIdString}",
            "playerId": "#{playerIdStringA}",
            "robotId": "d290f1ee-6c54-4b01-90e6-d701748f0851",
            "commandType": "blocking",
            "commandObject": {
              "commandType": "blocking",
              "planetId": "d290f1ee-6c54-4b01-90e6-d701748f0851",
              "targetId": "d290f1ee-6c54-4b01-90e6-d701748f0851",
              "itemName": "string"
            }
          },
          {
            "transactionId": "#{transactionIdStringB}",
            "gameId": "#{gameIdString}",
            "playerId": "#{playerIdStringB}",
            "robotId": "d290f1ee-6c54-4b01-90e6-d701748f0851",
            "commandType": "blocking",
            "commandObject": {
              "commandType": "blocking",
              "planetId": "d290f1ee-6c54-4b01-90e6-d701748f0851",
              "targetId": "d290f1ee-6c54-4b01-90e6-d701748f0851",
              "itemName": "string"
            }
          }
        ]
      }
      """;
    body = body.replace("#{gameIdString}", gameIdString);
    body = body.replace("#{roundIdString}", roundIdString);
    body = body.replace("#{transactionIdStringA}", transactionIdStringA);
    body = body.replace("#{playerIdStringA}", playerIdStringA);
    body = body.replace("#{transactionIdStringB}", transactionIdStringB);
    body = body.replace("#{playerIdStringB}", playerIdStringB);
    return body;
  }
}