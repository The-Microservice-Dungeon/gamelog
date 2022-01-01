package com.github.tmd.gamelog.application;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import com.github.tmd.gamelog.WireMockInitializer;
import com.github.tmd.gamelog.adapter.jpa.history.command.CommandHistoryJpaRepository;
import com.github.tomakehurst.wiremock.WireMockServer;
import java.util.UUID;
import java.util.stream.StreamSupport;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = WireMockInitializer.class)
@Transactional
class CommandHistoryServiceTest {

  @Autowired
  WireMockServer wireMockServer;

  @Autowired
  CommandHistoryService commandHistoryService;

  @Autowired
  CommandHistoryJpaRepository commandHistoryJpaRepository;

  @Test
  void shouldSaveExecutedCommands() {
    // Given
    var gameId = UUID.fromString("d290f1ee-6c54-4b01-90e6-d701748f0851");
    var roundNumber = 1;
    wireMockServer.stubFor(get(urlPathEqualTo("/commands"))
        .withQueryParam("gameId", equalTo(gameId.toString()))
        .withQueryParam("roundNumber", equalTo("1"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("""
                {
                  "gameId": "d290f1ee-6c54-4b01-90e6-d701748f0851",
                  "roundId": "ab58d128-1802-4bd2-88e1-2f9d88094c5c",
                  "roundNumber": 1,
                  "commands": [
                    {
                      "transactionId": "8ade28ee-31c0-480c-bc84-814072545dd7",
                      "gameId": "d290f1ee-6c54-4b01-90e6-d701748f0851",
                      "playerId": "e1f5e4df-d35c-4ae4-b444-bc1f940f719d",
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
                      "transactionId": "56e876dc-0ec3-48df-8d62-89044c9dbef7",
                      "gameId": "d290f1ee-6c54-4b01-90e6-d701748f0851",
                      "playerId": "175d2c20-bfa2-41fd-8eac-a564850658a6",
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
                """)
        )
    );

    // When
    this.commandHistoryService.getAndSaveAllExecutedCommandsInRound(gameId, roundNumber);

    // Then
    var commands = this.commandHistoryJpaRepository.findAll();
    assertThat(commands).hasSize(2);
    assertThat(commands).extracting("transactionId", "gameId", "roundId", "playerId")
        .containsExactlyInAnyOrder(
            tuple(UUID.fromString("8ade28ee-31c0-480c-bc84-814072545dd7"),
                UUID.fromString("d290f1ee-6c54-4b01-90e6-d701748f0851"),
                UUID.fromString("ab58d128-1802-4bd2-88e1-2f9d88094c5c"),
                UUID.fromString("e1f5e4df-d35c-4ae4-b444-bc1f940f719d")),
            tuple(UUID.fromString("56e876dc-0ec3-48df-8d62-89044c9dbef7"),
                UUID.fromString("d290f1ee-6c54-4b01-90e6-d701748f0851"),
                UUID.fromString("ab58d128-1802-4bd2-88e1-2f9d88094c5c"),
                UUID.fromString("175d2c20-bfa2-41fd-8eac-a564850658a6"))
        );
  }
}