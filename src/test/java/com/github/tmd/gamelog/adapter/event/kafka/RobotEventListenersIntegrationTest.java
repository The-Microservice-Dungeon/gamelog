package com.github.tmd.gamelog.adapter.event.kafka;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.after;
import static org.mockito.Mockito.verify;

import com.github.tmd.gamelog.adapter.event.gameEvent.map.ResourceType;
import com.github.tmd.gamelog.application.history.RobotHistoryService;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("integration")
@SpringBootTest(properties = { "spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer" })
@DirtiesContext
@EnabledIfEnvironmentVariable(disabledReason = "Should run in CI", named = "CI", matches = "true")
class RobotEventListenersIntegrationTest {

  @MockBean
  RobotHistoryService robotHistoryService;

  @Autowired
  KafkaTemplate<String, Object> template;

  @Test
  void shouldConsumeMovementEvent() {
    // Given
    var transactionId = UUID.randomUUID();
    var payload = """
        {
          "success": true,
          "message": "Movement successfull/Planet does not exist",
          "remainingEnergy": 10,
          "planet": {
            "planetId": "5e7773ed-265a-4e7b-bece-86392407afef",
            "movementDifficulty": 5,
            "planetType": "DEFAULT",
            "resourceType": "COAL"
          },
          "robots": [
            "497f6eca-6276-4993-bfeb-53cbbbba6f08"
          ]
        }
        """;
    var key = UUID.randomUUID().toString();

    var producerRecord = KafkaProducerRecordBuilder.<String, Object>builder()
        .topic("movement")
        .payload(payload)
        .key(key)
        .eventId(UUID.randomUUID().toString())
        .transactionId(transactionId.toString())
        .type("movement")
        .build().toProducerRecord();

    // When
    template.send(producerRecord);

    // Then
    ArgumentCaptor<Set> robotArg = ArgumentCaptor.forClass(Set.class);
    verify(robotHistoryService, after(5000).only()).insertMovementHistory(eq(transactionId), robotArg.capture(), eq(UUID.fromString("5e7773ed-265a-4e7b-bece-86392407afef")), eq(5), any());
    assertThat(robotArg.getValue()).containsExactlyInAnyOrder(UUID.fromString("497f6eca-6276-4993-bfeb-53cbbbba6f08"));
  }
/*
  @Test
  void shouldConsumePlanetBlockedEvent() {
    // Given
    var transactionId = UUID.randomUUID();
    var payload = """
        {
          "success": true,
          "message": "Planet successfully blocked/Not enough energy",
          "planetId": "5e7773ed-265a-4e7b-bece-86392407afef",
          "remainingEnergy": 10
        }
        """;
    var key = UUID.randomUUID().toString();

    var producerRecord = KafkaProducerRecordBuilder.<String, Object>builder()
        .topic("planet-blocked")
        .payload(payload)
        .key(key)
        .eventId(UUID.randomUUID().toString())
        .transactionId(transactionId.toString())
        .type("planet-blocked")
        .build().toProducerRecord();

    // When
    template.send(producerRecord);

    // Then
    verify(robotHistoryService, after(5000).only()).insertPlanetBlockHistory(eq(transactionId), eq(UUID.fromString("5e7773ed-265a-4e7b-bece-86392407afef")), any());
  }*/

  @Test
  void shouldConsumeMiningEvent() {
    // Given
    var transactionId = UUID.randomUUID();
    var payload = """
        {
          "success": true,
          "message": "Mining successful/Not enough energy",
          "remainingEnergy": 10,
          "updatedInventory": 5,
          "resourceType": "COAL"
        }
        """;
    var key = UUID.randomUUID().toString();

    var producerRecord = KafkaProducerRecordBuilder.<String, Object>builder()
        .topic("mining")
        .payload(payload)
        .key(key)
        .eventId(UUID.randomUUID().toString())
        .transactionId(transactionId.toString())
        .type("mining")
        .build().toProducerRecord();

    // When
    template.send(producerRecord);

    // Then
    verify(robotHistoryService, after(5000).only()).insertMiningHistory(eq(transactionId), eq(5), eq(
        ResourceType.COAL), any());
  }

  @Test
  void shouldConsumeFightingEvent() {
    // Given
    var transactionId = UUID.randomUUID();
    var payload = """
        {
          "success": true,
          "message": "Attacking successful/Target not found",
          "attacker": "afcf1d7a-29fc-47b4-8832-265a3473f8d8",
          "defender": "3f6ad423-cc3c-4d3f-89cb-bdf0badfd437",
          "remainingDefenderHealth": 10,
          "remainingEnergy": 10
        }
        """;
    var key = UUID.randomUUID().toString();

    var producerRecord = KafkaProducerRecordBuilder.<String, Object>builder()
        .topic("fighting")
        .payload(payload)
        .key(key)
        .eventId(UUID.randomUUID().toString())
        .transactionId(transactionId.toString())
        .type("fighting")
        .build().toProducerRecord();

    // When
    template.send(producerRecord);

    // Then
    verify(robotHistoryService, after(5000).only()).insertFightHistory(eq(transactionId), eq(UUID.fromString("afcf1d7a-29fc-47b4-8832-265a3473f8d8")), eq(
        UUID.fromString("3f6ad423-cc3c-4d3f-89cb-bdf0badfd437")), eq(10), any());
  }
}